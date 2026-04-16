import fs from "node:fs";
import path from "node:path";
import { MongoClient, EJSON } from "mongodb";

function readEnvValue(fileContent, key) {
  const escaped = key.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
  const match = fileContent.match(new RegExp(`^${escaped}=(.*)$`, "m"));
  return match ? match[1].trim() : "";
}

function timestamp() {
  const d = new Date();
  const pad = (n) => String(n).padStart(2, "0");
  return `${d.getFullYear()}${pad(d.getMonth() + 1)}${pad(d.getDate())}-${pad(d.getHours())}${pad(d.getMinutes())}${pad(d.getSeconds())}`;
}

async function dumpDbToJson(db, outputDir) {
  fs.mkdirSync(outputDir, { recursive: true });
  const collections = await db.listCollections({}, { nameOnly: true }).toArray();

  for (const { name } of collections) {
    const docs = await db.collection(name).find({}).toArray();
    const filePath = path.join(outputDir, `${name}.json`);
    fs.writeFileSync(filePath, EJSON.stringify(docs, null, 2), "utf8");
    console.log(`[backup] ${name}: ${docs.length} docs`);
  }
}

async function copyDb(sourceDb, targetDb) {
  const collections = await sourceDb.listCollections({}, { nameOnly: true }).toArray();

  for (const { name } of collections) {
    const docs = await sourceDb.collection(name).find({}).toArray();
    const targetCol = targetDb.collection(name);

    await targetCol.deleteMany({});
    if (docs.length > 0) {
      await targetCol.insertMany(docs, { ordered: false });
    }
    console.log(`[copy] ${name}: ${docs.length} docs`);
  }
}

async function main() {
  const envPath = path.resolve("backend/.env");
  const envContent = fs.readFileSync(envPath, "utf8");
  const atlasUri = readEnvValue(envContent, "MONGODB_URI");
  const dbName = "arabic_helper";

  if (!atlasUri) {
    throw new Error("backend/.env 에 MONGODB_URI 값이 없습니다.");
  }

  const localUri = "mongodb://127.0.0.1:27017";
  const localClient = new MongoClient(localUri);
  const atlasClient = new MongoClient(atlasUri);

  try {
    await localClient.connect();
    await atlasClient.connect();
    await localClient.db("admin").command({ ping: 1 });
    await atlasClient.db("admin").command({ ping: 1 });

    console.log("Connected: local + atlas");

    const localDb = localClient.db(dbName);
    const atlasDb = atlasClient.db(dbName);

    const backupRoot = path.resolve("backend/db-backups");
    const backupDir = path.join(backupRoot, `atlas-${dbName}-${timestamp()}`);
    await dumpDbToJson(atlasDb, backupDir);

    await copyDb(localDb, atlasDb);
    console.log(`Done. Atlas backup saved at: ${backupDir}`);
  } finally {
    await localClient.close();
    await atlasClient.close();
  }
}

main().catch((err) => {
  console.error(err?.message || err);
  process.exit(1);
});

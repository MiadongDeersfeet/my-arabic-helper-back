import mongoose from "mongoose";

const { Schema } = mongoose;

const sentenceCardSchema = new Schema(
  {
    cardNo: { type: Number, required: true, min: 1 },
    categoryId: { type: Schema.Types.ObjectId, required: true, ref: "Category" },
    categoryName: { type: String, required: true, trim: true },
    kor: {
      text: { type: String, required: true },
      audioUrl: { type: String, required: true }
    },
    arb: {
      text: { type: String, required: true },
      audioUrl: { type: String, required: true }
    },
    flow: {
      countdownSec: { type: Number, required: true, min: 1, default: 10 }
    },
    active: { type: Boolean, default: true }
  },
  {
    collection: "sentence_cards",
    timestamps: { createdAt: "createdAt", updatedAt: "updatedAt" }
  }
);

sentenceCardSchema.index({ categoryId: 1, cardNo: 1 });

export default mongoose.model("SentenceCard", sentenceCardSchema);

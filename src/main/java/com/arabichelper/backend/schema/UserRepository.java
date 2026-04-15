package com.arabichelper.backend.schema;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// 회원 스키마 전용 Repository
public interface UserRepository extends MongoRepository<UserSchema, String> {

    // 아이디 중복 체크/로그인 조회에 사용
    boolean existsByUserId(String userId);

    // 로그인 시 user_id 기준 사용자 조회
    Optional<UserSchema> findByUserId(String userId);

    // user_no 자동 부여 시 마지막 번호 조회
    Optional<UserSchema> findTopByOrderByUserNoDesc();
}

package com.arabichelper.backend.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

// 회원(사용자) 스키마 문서
@Data
@Document(collection = "users")
public class UserSchema {

    @Id
    private String id;

    // 사용자 번호
    @Field("user_no")
    private Long userNo;

    // 로그인 아이디
    @NotBlank(message = "user_id 값은 필수입니다.")
    @Field("user_id")
    @Indexed(unique = true)
    private String userId;

    // 비밀번호(추후 BCrypt 해시 저장 권장)
    @NotBlank(message = "password 값은 필수입니다.")
    @Field("password")
    private String password;

    // 권한(admin 또는 user)
    @NotBlank(message = "role 값은 필수입니다.")
    @Pattern(regexp = "admin|user", message = "role은 admin 또는 user만 가능합니다.")
    @Field("role")
    private String role;
}

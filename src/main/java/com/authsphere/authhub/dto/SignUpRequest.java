package com.authsphere.authhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Size(max = 30, message = "이메일은 최대 30자 이내로 입력해주세요.")
    String email,
    
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 이내로 입력해주세요.")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?]).*$",
        message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다."
    )
    String password
) {
    // 빌더 패턴 추가
    public static class Builder {
        private String email;
        private String password;
        
        public Builder email(String email) {
            this.email = email.trim();
            return this;
        }
        
        public Builder password(String password) {
            this.password = password.trim();
            return this;
        }
        
        public SignUpRequest build() {
            return new SignUpRequest(email, password);
        }
    }

    // 트림 처리
    public SignUpRequest {
        email = email != null ? email.trim() : null;
        password = password != null ? password.trim() : null;
    }
}

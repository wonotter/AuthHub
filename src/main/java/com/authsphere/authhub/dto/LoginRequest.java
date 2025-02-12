package com.authsphere.authhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해야 합니다.")
    String email,
    
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 이내로 입력해주세요.")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\",./<>?]).*$",
        message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다."
    )
    String password
) {
    
}

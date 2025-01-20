package com.authsphere.authhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank(message = "username은 필수 값입니다.")
    @Size(min = 2, max = 20, message = "username은 2자 이상 20자 이하로 입력해야 합니다.")
    String username,
    
    @NotBlank(message = "password는 필수 값입니다.")
    @Size(min = 8, message = "password는 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+=-]).*$", message = "password는 영문, 숫자, 특수문자를 포함해야 합니다.")
    String password
) {
    
}

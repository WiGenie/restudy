package com.teamsparta.restudy.domain.member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class MemberRequest(

    val email: String,

    @field:NotBlank(message = "닉네임은 필수 입력 항목이에요.")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-z0-9]).{3,}$",
        message = "닉네임은 3자 이상이며 영어 대/소문자, 숫자로만 이루어져야 합니다."
    )
    val nickname: String,

    @field:NotBlank(message = "비밀번호는 필수 입력 항목이에요.")
    @field:Pattern(regexp = "^(?=.*.{4,})")
    val password: String,

    @field:NotBlank(message = "비밀번호 확인은 필수 입력 항목이에요.")
    @field:Pattern(regexp = "^(?=.*.{4,})")
    val rePassword: String,

    )

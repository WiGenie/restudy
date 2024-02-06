package com.teamsparta.restudy.domain.post.dto

import jakarta.validation.constraints.Pattern

data class PostRequest(

    @field:Pattern(
        regexp = "^(?=.*.{1,500}$)",
        message = "제목은 필수 입력 항목이며 500자 이하로 입력해주세요."
    )
    val title: String,

    @field:Pattern(
        regexp = "^(?=.*.{1,5000}$)",
        message = "내용은 필수 입력 항목이며 5000자 이하로 작성해주세요."
    )
    val content: String,
)
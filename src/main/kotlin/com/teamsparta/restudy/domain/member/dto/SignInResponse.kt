package com.teamsparta.restudy.domain.member.dto

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String,
)
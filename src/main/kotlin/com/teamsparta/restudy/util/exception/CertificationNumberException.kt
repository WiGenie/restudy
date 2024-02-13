package com.teamsparta.restudy.util.exception

data class CertificationNumberException(
    override val message: String= "인증번호가 일치하지 않아요."
) : RuntimeException(message)

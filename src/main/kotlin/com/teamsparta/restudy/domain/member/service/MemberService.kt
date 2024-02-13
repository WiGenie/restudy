package com.teamsparta.restudy.domain.member.service

import com.teamsparta.restudy.domain.member.dto.MemberRequest
import com.teamsparta.restudy.domain.member.dto.MemberResponse
import com.teamsparta.restudy.domain.member.dto.SignInResponse

interface MemberService {

    /**회원가입*/
    fun signUp(request: MemberRequest): MemberResponse

    /**로그인*/
    fun signIn(request: MemberRequest): SignInResponse

}

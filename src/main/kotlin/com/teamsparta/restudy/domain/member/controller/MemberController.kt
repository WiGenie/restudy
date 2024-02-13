package com.teamsparta.restudy.domain.member.controller

import com.teamsparta.restudy.domain.member.dto.MemberRequest
import com.teamsparta.restudy.domain.member.dto.MemberResponse
import com.teamsparta.restudy.domain.member.dto.SignInResponse
import com.teamsparta.restudy.domain.member.service.MemberServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/members")
class MemberController(
    private val memberServiceImpl: MemberServiceImpl
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody memberRequest: MemberRequest): ResponseEntity<MemberResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberServiceImpl.signUp(memberRequest))
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody memberRequest: MemberRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberServiceImpl.signIn(memberRequest))
    }

}
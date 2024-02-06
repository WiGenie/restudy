package com.teamsparta.restudy.domain.member.service

import com.teamsparta.restudy.domain.member.dto.MemberRequest
import com.teamsparta.restudy.domain.member.dto.MemberResponse
import com.teamsparta.restudy.domain.member.dto.SignInResponse
import com.teamsparta.restudy.domain.member.model.Member
import com.teamsparta.restudy.domain.member.model.MemberRole
import com.teamsparta.restudy.domain.member.model.toResponse
import com.teamsparta.restudy.domain.member.repository.MemberRepository
import com.teamsparta.restudy.util.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
) {

    @Transactional
    fun signUp(request: MemberRequest): MemberResponse {
        if (request.password.contains(request.nickname)) throw IllegalArgumentException("닉네임이 포함된 비밀번호는 사용할 수 없어요.")
        if (request.password != request.rePassword) throw IllegalArgumentException("비밀번호가 일치하지 않아요")
        if (memberRepository.existsByNickname(request.nickname)) throw IllegalArgumentException("사용 중인 닉네임이에요.")

        return memberRepository.save(
            Member(
                email = request.email,
                nickname = request.nickname,
                password = passwordEncoder.encode(request.password),
                role = MemberRole.USER
//                validate = Validate.NOT
            )
        ).toResponse()
        // 챌린지- 닉네임 존재 여부 확인 버튼을 눌러 먼저 유효성 검사?
        // 챌린지- 이메일을 통해 인증번호 받고 5분 내에 검증해야 회원가입 성공 로직?
    }

    fun signIn(request: MemberRequest): SignInResponse {
        val member =
            memberRepository.findByNickname(request.nickname) ?: throw IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요.")
        if (!passwordEncoder.matches(
                request.password,
                member.password
            )
        ) throw IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요.")

        return SignInResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                nickname = member.nickname,
                role = member.role.name,
            ),
            refreshToken = jwtPlugin.generateRefreshToken(
                subject = member.id.toString(),
                nickname = member.nickname,
                role = member.role.name,
            )
        )
        // 챌린지? - 클라이언트에 Cookie로 반환
    }

}
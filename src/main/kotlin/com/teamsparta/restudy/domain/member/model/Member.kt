package com.teamsparta.restudy.domain.member.model

import com.teamsparta.restudy.domain.member.dto.MemberResponse
import jakarta.persistence.*

@Entity
@Table(name = "members")
class Member(

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: MemberRole,

//    @Enumerated(EnumType.STRING)
//    @Column(name = "validate", nullable = false)
//    val validate: MemberValidate,

) {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null
}

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = id!!,
        email = email,
        nickname = nickname,
    )
}
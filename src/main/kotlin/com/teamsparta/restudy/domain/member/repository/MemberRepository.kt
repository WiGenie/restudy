package com.teamsparta.restudy.domain.member.repository

import com.teamsparta.restudy.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByNickname(nickname: String): Boolean

    fun findByNickname(nickname: String): Member?
}
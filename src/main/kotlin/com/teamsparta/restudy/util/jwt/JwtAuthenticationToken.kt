package com.teamsparta.restudy.util.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails
import java.io.Serializable

class JwtAuthenticationToken(
    private val principal: MemberPrincipal,
    details: WebAuthenticationDetails,
): AbstractAuthenticationToken(principal.authorities), Serializable {

    init {
        super.setAuthenticated(true)
        super.setDetails(details)
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun getCredentials() = null

    override fun getPrincipal() = principal
}
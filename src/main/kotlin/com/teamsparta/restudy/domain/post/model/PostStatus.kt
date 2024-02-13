package com.teamsparta.restudy.domain.post.model

import com.fasterxml.jackson.annotation.JsonCreator
import org.apache.commons.lang3.EnumUtils

enum class PostStatus {
    POSTED, DELETED, WAIT;

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun parse(name: String?): PostStatus? =
            name?.let { EnumUtils.getEnumIgnoreCase(PostStatus::class.java, it.trim()) }
    } // 이걸 이용하면 컨트롤러에서 값을 입력받을 때 받은 값을 바로 이넘 클래스 값으로 사용 가능
}

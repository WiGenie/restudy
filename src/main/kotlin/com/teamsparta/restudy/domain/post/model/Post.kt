package com.teamsparta.restudy.domain.post.model

import com.teamsparta.restudy.domain.comment.dto.CommentResponse
import com.teamsparta.restudy.domain.member.model.Member
import com.teamsparta.restudy.domain.post.dto.PostResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "Posts")
class Post(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "created_at", columnDefinition = "TIMESTAMP(0)", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column
    @Enumerated(EnumType.STRING)
    var status: PostStatus,

    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member


) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun Post.toResponse(comments: List<CommentResponse>): PostResponse {
    return PostResponse(
        id = id!!,
        title = title,
        nickname = member.nickname,
        content = content,
        createdAt = createdAt,
        comments = comments
    )
}
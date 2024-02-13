package com.teamsparta.restudy.domain.comment.model

import com.teamsparta.restudy.domain.comment.dto.CommentResponse
import com.teamsparta.restudy.domain.member.model.Member
import com.teamsparta.restudy.domain.post.model.Post
import com.teamsparta.restudy.util.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment(

    @Column(name = "content", nullable = false)
    var content: String,

//    @Column(name = "created_at", columnDefinition = "TIMESTAMP(0)", nullable = false, updatable = false)
//    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "status", nullable = false)
    @Enumerated
    var status: CommentStatus = CommentStatus.POSTED,

    @JoinColumn(name = "member_id")
    @ManyToOne
    val member: Member,

    @JoinColumn(name = "post_id")
    @ManyToOne
    val post: Post,


    ) : BaseEntity() {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        nickname = member.nickname,
        content = content,
        createdAt = createdAt,
    )

}
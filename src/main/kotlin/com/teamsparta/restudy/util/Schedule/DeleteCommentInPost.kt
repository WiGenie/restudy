package com.teamsparta.restudy.util.Schedule

import com.teamsparta.restudy.domain.comment.model.CommentStatus
import com.teamsparta.restudy.domain.comment.repository.CommentRepository
import com.teamsparta.restudy.domain.comment.service.CommentService
import com.teamsparta.restudy.domain.post.model.Post
import com.teamsparta.restudy.domain.post.model.PostStatus
import com.teamsparta.restudy.domain.post.repository.PostRepository
import com.teamsparta.restudy.domain.post.service.PostService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeleteCommentInPost(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {

    @Transactional
    @Scheduled(cron = "* * * * *")
    fun findWaitPost() {

        val posts = postRepository.findAllByStatus(PostStatus.WAIT)

        for (post in posts){
            if (post.id == null) continue
            val comments = commentRepository.findAllByPostId(post.id)

            for (i in comments) {
                i.status = CommentStatus.DELETED
            }
            post.status = PostStatus.DELETED
            postRepository.save(post)
            commentRepository.saveAll(comments)
        }
    }
}

//private fun deleteAllCommentInPost(postId: Long) {
//    val comments = commentRepository.findAllByPostId(postId)
//    for (i in comments){
//        i.status= CommentStatus.DELETED
//    }
//    commentRepository.saveAll(comments)
//}
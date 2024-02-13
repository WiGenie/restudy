package com.teamsparta.restudy.util.Schedule

import com.teamsparta.restudy.domain.comment.model.CommentStatus
import com.teamsparta.restudy.domain.comment.repository.CommentRepository
import com.teamsparta.restudy.domain.post.model.PostStatus
import com.teamsparta.restudy.domain.post.repository.PostRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeleteCommentInPost(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {

    @Transactional
    @Scheduled(cron = "0 0 * * *")
//    Schedule vs batch .jar(실행파일)
//    -> 스케쥴은 이렇게 한 파일 내에서 실행, 수정시 재배포가 필요함(번거로움)
//    -> 배치는 따로 @@.jar 파일을 만들고 이를 실행, 값 수정에 비교적 자유로움
//    => 간단하고 수정할 일이 거의 없는 것에 대해 스케쥴, 아니라면 배치

//    fun findDeletePostAndDeleteComment() {
    fun findDeletePost(){
//        1. post테이블에서 마지막 삭제일을 조회해온다. deletedDate

//        val posts = postRepository.findByCreatedAtBeforeAndStatus(deletedDate, PostStatus.DELETED)
        val posts = postRepository.findAllByStatus(PostStatus.WAIT)
        //삭제 대기중인 글들

        for (post in posts){
            if (post.id == null) continue
            val comments = commentRepository.findAllByPostId(post.id)
            //삭제 대기중인 게시글 하위 댓글들

            for (i in comments) {
                i.status = CommentStatus.DELETED
            }
            post.status = PostStatus.DELETED
//            postRepository.save(post)  //jpa dirty checking
//            commentRepository.saveAll(comments)
//            -> Transactional이 있으면 변동이 있을 때 끝나면서 알아서 저장해주므로 필요없다
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
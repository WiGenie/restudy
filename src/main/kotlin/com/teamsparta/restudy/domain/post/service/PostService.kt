package com.teamsparta.restudy.domain.post.service

import com.teamsparta.restudy.domain.comment.dto.CommentResponse
import com.teamsparta.restudy.domain.comment.model.CommentStatus
import com.teamsparta.restudy.domain.comment.repository.CommentRepository
import com.teamsparta.restudy.domain.member.repository.MemberRepository
import com.teamsparta.restudy.domain.post.dto.PostListResponse
import com.teamsparta.restudy.domain.post.dto.PostRequest
import com.teamsparta.restudy.domain.post.dto.PostResponse
import com.teamsparta.restudy.domain.post.model.Post
import com.teamsparta.restudy.domain.post.model.PostStatus
import com.teamsparta.restudy.domain.post.model.toResponse
import com.teamsparta.restudy.domain.post.repository.PostRepository
import com.teamsparta.restudy.util.exception.ModelNotFoundException
import com.teamsparta.restudy.util.jwt.MemberPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.typeOf

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository,
) {

//    fun getPostList(): List<PostListResponse> {
//        return postRepository.findAll()
//            .filter { it.status == PostStatus.POSTED }
//            .map {
//                PostListResponse(
//                    id = it.id!!,
//                    title = it.title,
//                    nickname = it.member.nickname,
//                    createdAt = it.createdAt,
//                )
//            }
//            .sortedByDescending { it.createdAt }
//    }
    // 챌린지 - 페이징 조회(밑의 번호를 누르면 순서대로 10개씩 나오는 그런거)
    // 챌린지 - 페이징 + 커스텀 정렬 ( 오름차순/ 내림차순/ 작성자명/ n개까지만 출력 등)

    fun getPostList(page: Int): Page<PostListResponse> {
        if (page <= 0) throw IllegalArgumentException("페이지는 1부터 시작해요.")
        val pageable: Pageable= PageRequest.of(page-1, 10, Sort.by("created_at").descending())
        val postPage: Page<Post> = postRepository.findAllByStatus(PostStatus.POSTED, pageable)
        if (postPage.isEmpty) throw ModelNotFoundException("postPage")
        return postPage
            .map {
                PostListResponse(
                    id = it.id!!,
                    title = it.title,
                    nickname = it.member.nickname,
                    createdAt = it.createdAt
                )
            }
    }

    fun getPost(
        postId: Long,
    ): PostResponse {
        val post = getPostInfo(postId)
        if (post.status != PostStatus.POSTED) {
            throw ModelNotFoundException("postId")
        }
        val comments = getCommentInPost(postId)
        return post.toResponse(comments)
    }

    @Transactional
    fun createPost(
        request: PostRequest,
        memberPrincipal: MemberPrincipal,
    ) {
        val member = getMemberInfo(memberPrincipal.id)

        postRepository.save(
            Post(
                title = request.title,
                member = member,
                content = request.content,
                status = PostStatus.POSTED,
            )
        )
    }
    // 챌린지 - 이미지 업로드

    @Transactional
    fun updatePost(
        postId: Long,
        request: PostRequest,
        memberPrincipal: MemberPrincipal,
    ) {
        val post = getPostInfo(postId)
        val member = getMemberInfo(memberPrincipal.id)

        if (post.member != member) {
            throw IllegalArgumentException("작성자만 수정이 가능해요.")
        } else {
            val (title, content) = request
            post.title = title
            post.content = content

            postRepository.save(post)
        }
    }

    @Transactional
    fun deletePost(
        postId: Long,
        memberPrincipal: MemberPrincipal,
    ): Unit {
        val post = getPostInfo(postId)
        val member = getMemberInfo(memberPrincipal.id)
        if (post.member != member) {
            throw IllegalArgumentException("작성자만 삭제가 가능해요")
        } else {
            post.status = PostStatus.WAIT
            postRepository.save(post)
//            deleteAllCommentInPost(postId)
        }
    }
//    챌린지 - 수정 90일이 지난 데이터 자동 삭제 기능


    private fun getPostInfo(postId: Long) =
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("postId")

    private fun getMemberInfo(memberId: Long) =
        memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("memberId")

    private fun getCommentInPost(postId: Long): List<CommentResponse> {
        val comments = commentRepository.findAllByPostId(postId)
        return comments.filter { it.status == CommentStatus.POSTED }
            .map {
                CommentResponse(
                    id = it.id!!,
                    nickname = it.member.nickname,
                    content = it.content,
                    createdAt = it.createdAt
                )
            }
    }

//    private fun deleteAllCommentInPost(postId: Long) {
//        val comments = commentRepository.findAllByPostId(postId)
//        for (i in comments){
//            i.status= CommentStatus.DELETED
//        }
//        commentRepository.saveAll(comments)
//    }
}
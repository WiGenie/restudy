package com.teamsparta.restudy.domain.post.repository

import com.querydsl.core.BooleanBuilder
import com.teamsparta.restudy.domain.post.dto.PostListResponse
import com.teamsparta.restudy.domain.post.model.Post
import com.teamsparta.restudy.domain.post.model.PostStatus
import com.teamsparta.restudy.domain.post.model.QPost
import com.teamsparta.restudy.util.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl : QueryDslSupport(), CustomPostRepository {

    private val post = QPost.post

    override fun searchPostListByTitle(title: String): List<Post> {
        return queryFactory.selectFrom(post)
            .where(post.title.containsIgnoreCase(title)) // containsIgnoreCase는 대소문자를 무시
            .fetch()
    }

    override fun findByPageableAndStatus(pageable: Pageable, postStatus: PostStatus): Page<PostListResponse> {
        val whereClause = BooleanBuilder()
        postStatus.let { whereClause.and(post.status.eq(postStatus)) }
//         status를 만약 체크한다면 확인하는 과정
        val totalCount = queryFactory.select(post.count()).from(post).where(whereClause).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(post)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
//            .orderBy(post.createdAt.desc())

        if (pageable.sort.isSorted) {
            when (pageable.sort.first()?.property) {
                "id" -> {
                    query.orderBy(post.id.asc())
                }

                "title" -> {
                    query.orderBy(post.title.asc())
                }

                else -> {
                    query.orderBy(post.id.asc())
                }
            }
        } else {
            query.orderBy(post.id.asc())
        }

        val content = query.fetch()

        return PageImpl(content, pageable, totalCount).map {
            PostListResponse(
                id = it.id!!,
                title = it.title,
                nickname = it.member.nickname,
                createdAt = it.createdAt
            )
        }
    }

}
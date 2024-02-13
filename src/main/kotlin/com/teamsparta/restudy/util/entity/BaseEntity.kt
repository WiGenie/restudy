package com.teamsparta.restudy.util.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at",
        columnDefinition = "TIMESTAMP(0)",
        nullable = false,
        updatable = false)
    val createdAt: LocalDateTime= LocalDateTime.now()

    @LastModifiedDate
    @Column(name = "updated_at",
        columnDefinition = "TIMESTAMP(0)")
    var updatedAt: LocalDateTime? = null


    @Column(name = "deleted_at",
        columnDefinition = "TIMESTAMP(0)")
    var deletedAt: LocalDateTime? = null
}
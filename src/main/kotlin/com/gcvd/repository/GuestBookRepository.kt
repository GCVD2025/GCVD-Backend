package com.gcvd.repository

import com.gcvd.entity.GuestBook
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GuestBookRepository : CrudRepository<GuestBook, String>{
    fun findAllByOrderByCreatedAtDesc(): List<GuestBook>
}
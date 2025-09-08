package com.gcvd.repository

import com.gcvd.entity.GuestBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GuestBookRepository : JpaRepository<GuestBook, Long>
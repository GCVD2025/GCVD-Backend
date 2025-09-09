package com.gcvd.service

import com.gcvd.dto.GuestBookDTO
import com.gcvd.entity.GuestBook
import com.gcvd.repository.GuestBookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GuestBookService(
    private val guestBookRepository: GuestBookRepository
) {

    @Transactional(readOnly = true)
    fun getAllGuestBooks(): List<GuestBookDTO.Output> {
        val list = mutableListOf<GuestBookDTO.Output>()
        val foundList = guestBookRepository.findAll()
        foundList.sortBy { guestBook -> guestBook.createdAt }
        foundList.forEach { guestBook: GuestBook ->
            list += GuestBookDTO.Output(
                guestBook.author,
                guestBook.content,
                guestBook.createdAt!!
            )
        }
        return list
    }

    @Transactional
    fun createGuestBook(dto: GuestBookDTO.Input): GuestBookDTO.Output {
        val guestBook = GuestBook(
            dto.author,
            dto.content,
        )

        val saved = guestBookRepository.save(guestBook)

        return GuestBookDTO.Output(
            saved.author,
            saved.content,
            saved.createdAt!!
        )
    }

}
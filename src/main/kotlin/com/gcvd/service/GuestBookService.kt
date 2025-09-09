package com.gcvd.service

import com.gcvd.dto.GuestBookDTO
import com.gcvd.entity.GuestBook
import com.gcvd.repository.GuestBookRepository
import org.springframework.stereotype.Service

@Service
class GuestBookService(
    private val guestBookRepository: GuestBookRepository
) {

    fun getAllGuestBooks(): List<GuestBookDTO.Output> {
        val foundList = guestBookRepository.findAllByOrderByCreatedAtDesc()

        return foundList.map { guestBook: GuestBook ->
            GuestBookDTO.Output(
                guestBook.author,
                guestBook.content,
                guestBook.createdAt
            )
        }
    }

    fun createGuestBook(dto: GuestBookDTO.Input): GuestBookDTO.Output {
        val guestBook = GuestBook(
            dto.author,
            dto.content
        )

        val saved = guestBookRepository.save(guestBook)

        return GuestBookDTO.Output(
            saved.author,
            saved.content,
            saved.createdAt
        )
    }
}

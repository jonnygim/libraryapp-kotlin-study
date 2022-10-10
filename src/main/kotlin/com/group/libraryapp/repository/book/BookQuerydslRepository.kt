package com.group.libraryapp.repository.book

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory,
) {

    fun getStats(): List<BookStatResponse> {
        return  queryFactory
            .select(
                Projections.constructor( // 데이터 특정 컬럼 가져오기 위해 (주어진 DTO 생성자 호출)
                    BookStatResponse::class.java,
                    book.type,
                    book.id.count()
                )
            )
            .from(book)
            .groupBy(book.type)
            .fetch()
    }

}
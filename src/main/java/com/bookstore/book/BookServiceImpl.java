package com.bookstore.book;

import com.bookstore.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public PageResponse<BookResponse> getAllSharableBook(int page, int size) {
        Long currentUserId = 1L; // TODO GET FROM SECURITY CONTEXT HOLDER

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAll(pageable, currentUserId);
        List<BookResponse> bookResponses = bookMapper.toBookResponses(books);

        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    @Override
    public PageResponse<BookResponse> getAllArchivedBookByOwner(int page, int size) {
        return null;
    }

    @Override
    public void archiveBook(Long bookId) {

    }

    @Override
    public void makeBookShareable(Long bookId) {

    }

}

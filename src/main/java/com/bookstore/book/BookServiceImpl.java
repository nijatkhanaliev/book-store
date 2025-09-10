package com.bookstore.book;

import com.bookstore.common.PageResponse;
import com.bookstore.user.User;
import com.bookstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bookstore.security.SecurityUtil.getCurrentUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    @PreAuthorize("hasRole('SELLER')")
    public void createBook(BookRequest bookRequest) {
        User currentUser = getCurrentUser(userRepository);
        log.info("Creating new Book, ownerEmail {}", currentUser.getEmail());
        Book book = bookMapper.toBook(bookRequest);
        book.setBookStatus(BookStatus.PENDING);
        book.setOwner(currentUser);
        bookRepository.save(book);

        // TODO SEND EMAIL TO ADMIN APPROVE BOOK

    }

    @Override
    public PageResponse<BookResponse> getAllShareableBook(int page, int size) {
        User currentUser = getCurrentUser(userRepository);
        log.info("GetAllShareableBook by userEmail {}", currentUser.getEmail());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAll(pageable, currentUser.getId());
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

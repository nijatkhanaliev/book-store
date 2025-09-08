package com.bookstore.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
               select b from Book b where b.owner.id != :id
            """)
    Page<Book> findAll(Pageable pageable, @Param("id") Long currentUserId);
}

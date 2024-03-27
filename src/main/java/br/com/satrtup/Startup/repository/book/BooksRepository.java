package br.com.satrtup.Startup.repository.book;

import br.com.satrtup.Startup.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {}
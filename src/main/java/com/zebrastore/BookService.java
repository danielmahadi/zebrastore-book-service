/* (C)2025 */
package com.zebrastore;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Set;
import org.jboss.logging.Logger;

@ApplicationScoped
@Transactional
public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class);
    private final Validator validator;

    public BookService(Validator validator) {
        this.validator = validator;
    }

    public SaveToDBResult saveToDB(Book book) {
        try {
            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            if (!violations.isEmpty()) {
                var result = new SaveToDBResult();
                result.success = false;
                result.book = book;
                result.violations = violations;
                return result;
            }

            book.persistAndFlush();
            logger.info("Book created: " + book);

            var result = new SaveToDBResult();
            result.success = true;
            result.book = book;
            return result;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    public void saveBookOnDisk(Book book) throws FileNotFoundException {
        String bookJson = JsonbBuilder.create().toJson(book);
        try (PrintWriter out =
                new PrintWriter("book-%d.json".formatted(Instant.now().toEpochMilli()))) {
            out.println(bookJson);
        }
    }

    public Object getAllBooks() {
        return Book.listAll();
    }

    public Object getBookByIsbn13(String isbn13) {
        return Book.find("isbn13", isbn13).firstResult();
    }
}

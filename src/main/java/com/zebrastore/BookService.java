/* (C)2025 */
package com.zebrastore;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import org.jboss.logging.Logger;

@ApplicationScoped
@Transactional
public class BookService {
    @Inject Logger logger;

    public void saveToDB(Book book) {
        try {
            book.persistAndFlush();
            logger.info("Book created: " + book);
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
}

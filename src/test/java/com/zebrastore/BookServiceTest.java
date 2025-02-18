/* (C)2025 */
package com.zebrastore;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class BookServiceTest {

    @Inject Validator validator;

    @Test
    public void shouldValidateBook() {
        Book book = new Book();
        book.title = "";

        BookService bookService = new BookService(validator);
        var result = bookService.saveToDB(book);

        Assertions.assertFalse(result.success);
        Assertions.assertFalse(result.violations.isEmpty());
    }
}

/* (C)2025 */
package com.zebrastore;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Set;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("/api/books")
@Tag(name = "Book REST Endpoint")
public class BookResource {

    @RestClient IsbnServiceProxy isbnServiceProxy;
    @Inject Validator validator;
    @Inject Logger logger;

    @Inject BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all books")
    public Response getBooks() {
        return Response.ok(bookService.getAllBooks()).build();
    }

    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a book by ISBN 13")
    public Response getBookByIsbn(@PathParam("isbn") String isbn) {
        return Response.ok(bookService.getBookByIsbn13(isbn)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "Creates a new book")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(fallbackMethod = "fallbackOnCreatingABook")
    @Transactional
    public Response createABook(
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("year") int yearOfPublication,
            @FormParam("genre") String genre) {
        Book book = new Book();
        book.isbn13 = isbnServiceProxy.generateIsbnNumbers().isbn13;
        book.title = title;
        book.author = author;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        if (!violations.isEmpty()) {
            logger.warn(violations);
            ViolationResult<Book> violationResult = new ViolationResult<>();
            violationResult.setViolation(violations);
            return Response.status(400).entity(violationResult).build();
        }

        bookService.saveToDB(book);
        return Response.status(201).entity(book).build();
    }

    public Response fallbackOnCreatingABook(
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("year") int yearOfPublication,
            @FormParam("genre") String genre)
            throws FileNotFoundException {
        Book book = new Book();
        book.isbn13 = "--tbd--";
        book.title = title;
        book.author = author;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();
        bookService.saveBookOnDisk(book);
        logger.warn("Book saved on disk: " + book);
        return Response.status(206).entity(book).build();
    }
}

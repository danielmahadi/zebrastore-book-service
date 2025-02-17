/* (C)2025 */
package com.zebrastore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(indexes = {@Index(name = "isbn13_index", columnList = "isbn13")})
public class Book extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "book_id_seq")
    public Long id;

    @JsonbProperty("isbn_13")
    @NotBlank(message = "ISBN_EMPTY")
    @Schema(required = true)
    public String isbn13;

    @Schema(required = true)
    @NotBlank(message = "TITLE_EMPTY")
    public String title;

    public String author;

    @JsonbProperty("year_of_publication")
    public int yearOfPublication;

    public String genre;

    @JsonbDateFormat("yyyy/MM/dd")
    @JsonbProperty("creation_date")
    @Schema(implementation = String.class, format = "date")
    public Instant creationDate;

    @Override
    public String toString() {
        return "Book{isbn13='%s', title='%s', author='%s', yearOfPublication=%d, genre='%s', creationDate=%s}"
                .formatted(isbn13, title, author, yearOfPublication, genre, creationDate);
    }
}

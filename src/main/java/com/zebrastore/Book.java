/* (C)2025 */
package com.zebrastore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.Instant;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(indexes = {@Index(name = "isbn13_index", columnList = "isbn13")})
public class Book extends PanacheEntity {
    @JsonbProperty("isbn_13")
    @Schema(required = true)
    public String isbn13;

    @Schema(required = true)
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

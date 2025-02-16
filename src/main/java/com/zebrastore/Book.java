package com.zebrastore;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;

public class Book {
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


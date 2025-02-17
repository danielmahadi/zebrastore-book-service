/* (C)2025 */
package com.zebrastore;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void shouldCreateABook() {
        PanacheMock.mock(Book.class);

        given().formParam("title", "Adult Coloring Book: 40 Purrtastic Cats")
                .formParam("author", "Coloringcraze")
                .formParam("year", 2017)
                .formParam("genre", "Illustrated")
                .when()
                .post("/api/books")
                .then()
                .statusCode(201)
                .body("isbn_13", startsWith("13-"))
                .body("title", is("Adult Coloring Book: 40 Purrtastic Cats"))
                .body("author", is("Coloringcraze"))
                .body("year_of_publication", is(2017))
                .body("genre", is("Illustrated"))
                .body("creation_date", startsWith("20"));
    }
}

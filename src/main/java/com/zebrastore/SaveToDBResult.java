/* (C)2025 */
package com.zebrastore;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class SaveToDBResult {
    public boolean success;
    public Book book;
    public Set<ConstraintViolation<Book>> violations;
}

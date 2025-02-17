/* (C)2025 */
package com.zebrastore;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class ViolationResult<T> {
    @JsonbProperty("violations")
    public List<ViolationResultItem> violations = new java.util.ArrayList<>();

    public void setViolation(Set<ConstraintViolation<T>> violations) {
        for (ConstraintViolation<T> violation : violations) {
            ViolationResultItem item = new ViolationResultItem();
            item.propertyPath = violation.getPropertyPath().toString();
            item.messageTemplate = violation.getMessageTemplate();
            this.violations.add(item);
        }
    }
}

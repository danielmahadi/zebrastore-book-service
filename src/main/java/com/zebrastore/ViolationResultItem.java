/* (C)2025 */
package com.zebrastore;

import jakarta.json.bind.annotation.JsonbProperty;

public class ViolationResultItem {
    @JsonbProperty("property_path")
    public String propertyPath;

    @JsonbProperty("message_template")
    public String messageTemplate;
}

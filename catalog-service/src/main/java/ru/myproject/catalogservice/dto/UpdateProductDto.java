package ru.myproject.catalogservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProductDto(

        @NotBlank(message = "{catalog.product.error.update.not_valid.title.empty}")
        @Size(min = 3, max = 50, message = "{catalog.product.error.update.not_valid.title.size}")
        String title,

        @Size(max = 1000, message = "{catalog.product.error.update.not_valid.details.size}")
        String details) {
}
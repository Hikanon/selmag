package ru.myproject.catalogservice.dto;

public record ProductDto(
        Long id,
        String title,
        String details
) {
}
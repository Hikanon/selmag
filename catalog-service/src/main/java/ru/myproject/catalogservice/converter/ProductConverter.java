package ru.myproject.catalogservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.myproject.catalogservice.dto.ProductDto;
import ru.myproject.catalogservice.entity.Product;

@Component
public class ProductConverter implements Converter<Product, ProductDto> {
    @Override
    public ProductDto convert(Product source) {
        return new ProductDto(source.id(), source.title(), source.details());
    }
}

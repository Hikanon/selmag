package ru.myproject.catalogservice.service.api;


import ru.myproject.catalogservice.dto.NewProductDto;
import ru.myproject.catalogservice.dto.ProductDto;
import ru.myproject.catalogservice.dto.UpdateProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAllProducts();

    ProductDto getById(long productId);

    ProductDto createProduct(NewProductDto dto);

    void update(long productId, UpdateProductDto dto);

    void delete(long productId);
}

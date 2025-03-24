package ru.myproject.managerapp.client.api;

import ru.myproject.managerapp.dto.NewProductDto;
import ru.myproject.managerapp.dto.UpdateProductDto;
import ru.myproject.managerapp.entity.Product;
import ru.myproject.managerapp.exception.BadRequestException;

import java.util.List;

public interface ProductsRestClient {

    List<Product> findAllProducts();

    Product createProduct(NewProductDto newProductDto) throws BadRequestException;

    Product findById(long id);

    void updateProduct(long id, UpdateProductDto updateProductDto) throws BadRequestException;

    void delete(long id);
}

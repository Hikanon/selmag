package ru.myproject.managerapp.service.api;

import ru.myproject.managerapp.dto.NewProductDto;
import ru.myproject.managerapp.dto.UpdateProductDto;
import ru.myproject.managerapp.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product createProduct(NewProductDto newProductDto);

    Product findById(long id);

    void updateProduct(long id, UpdateProductDto updateProductDto);

    void delete(long id);

}

package ru.myproject.managerapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.myproject.managerapp.client.api.ProductsRestClient;
import ru.myproject.managerapp.dto.NewProductDto;
import ru.myproject.managerapp.dto.UpdateProductDto;
import ru.myproject.managerapp.entity.Product;
import ru.myproject.managerapp.service.api.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductsRestClient productsRestClient;

    @Override
    public List<Product> findAllProducts() {
        return productsRestClient.findAllProducts();
    }

    @Override
    public Product createProduct(NewProductDto newProductDto) {
        return productsRestClient.createProduct(newProductDto);
    }

    @Override
    public Product findById(long id) {
        return productsRestClient.findById(id);
    }

    @Override
    public void updateProduct(long id, UpdateProductDto updateProductDto) {
        productsRestClient.updateProduct(id, updateProductDto);
    }

    @Override
    public void delete(long id) {
        productsRestClient.delete(id);
    }
}

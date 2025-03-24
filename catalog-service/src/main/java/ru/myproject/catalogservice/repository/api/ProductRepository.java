package ru.myproject.catalogservice.repository.api;


import ru.myproject.catalogservice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(long productId);

    Product getById(long productId);

    Product save(Product product);

    void deleteById(long productId);
}

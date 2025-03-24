package ru.myproject.catalogservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myproject.catalogservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

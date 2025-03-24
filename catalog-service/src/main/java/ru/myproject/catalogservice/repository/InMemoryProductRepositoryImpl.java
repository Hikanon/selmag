package ru.myproject.catalogservice.repository;

import org.springframework.stereotype.Repository;
import ru.myproject.catalogservice.entity.Product;
import ru.myproject.catalogservice.repository.api.ProductRepository;

import java.util.*;

@Repository
public class InMemoryProductRepositoryImpl implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Optional<Product> findById(long productId) {
        return products.stream()
                .filter(product -> product
                        .id()
                        .equals(productId))
                .findFirst();
    }

    @Override
    public Product getById(long productId) {
        return findById(productId)
                .orElseThrow(() -> new NoSuchElementException("catalog.product.error.not_found.message"));
    }

    @Override
    public Product save(Product product) {
        products.stream()
                .filter(p -> Objects.equals(product.id(), p.id()))
                .findFirst()
                .ifPresentOrElse(p -> {
                    p.title(product.title());
                    p.details(product.details());
                }, () -> {
                    product.id(products
                            .stream()
                            .max(Comparator.comparingLong(Product::id))
                            .map(Product::id)
                            .orElse(0L) + 1L);
                    products.add(product);
                });
        return product;
    }

    @Override
    public void deleteById(long productId) {
        products.removeIf(product -> product.id().equals(productId));
    }
}

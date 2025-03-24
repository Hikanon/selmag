package ru.myproject.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.myproject.catalogservice.converter.ProductConverter;
import ru.myproject.catalogservice.dto.NewProductDto;
import ru.myproject.catalogservice.dto.ProductDto;
import ru.myproject.catalogservice.dto.UpdateProductDto;
import ru.myproject.catalogservice.entity.Product;
import ru.myproject.catalogservice.repository.ProductRepository;
import ru.myproject.catalogservice.service.api.ProductService;

import java.util.List;

@Validated
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productConverter::convert)
                .toList();
    }

    @Override
    public ProductDto getById(long productId) {
        Product product = productRepository.getReferenceById(productId);
        return productConverter.convert(product);
    }

    @Override
    public void update(long productId, UpdateProductDto dto) {
        productRepository.save(productRepository
                .getReferenceById(productId)
                .title(dto.title())
                .details(dto.details()));
    }

    @Override
    public ProductDto createProduct(NewProductDto dto) {
        Product product = productRepository
                .save(new Product(null,
                        dto.title(),
                        dto.details()));
        return productConverter.convert(product);
    }

    @Override
    public void delete(long productId) {
        productRepository.deleteById(productId);
    }
}

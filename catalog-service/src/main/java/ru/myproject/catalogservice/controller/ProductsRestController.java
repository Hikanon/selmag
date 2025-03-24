package ru.myproject.catalogservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.myproject.catalogservice.dto.NewProductDto;
import ru.myproject.catalogservice.dto.ProductDto;
import ru.myproject.catalogservice.service.api.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> findAllProducts() {
        return productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody NewProductDto newProduct,
                                                    BindingResult bindingResult,
                                                    UriComponentsBuilder uriComponentsBuilder)
            throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        ProductDto product = productService.createProduct(newProduct);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("/product/{productId}")
                        .build(Map.of("productId", product.id())))
                .body(product);
    }
}

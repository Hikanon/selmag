package ru.myproject.catalogservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.myproject.catalogservice.dto.ProductDto;
import ru.myproject.catalogservice.dto.UpdateProductDto;
import ru.myproject.catalogservice.service.api.ProductService;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products/{productId:\\d+}")
public class ProductRestController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public ProductDto getProduct(@Min(1L) @PathVariable long productId) {
        return productService.getById(productId);
    }

    @GetMapping
    public ProductDto findProduct(@ModelAttribute("product") ProductDto product) {
        return product;
    }

    @PatchMapping
    public ResponseEntity<Void> updateProduct(@Min(1L) @PathVariable long productId,
                                              @Valid @RequestBody UpdateProductDto editProduct,
                                              BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        productService.update(productId, editProduct);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@Min(1L) @PathVariable long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        messageSource.getMessage(exception.getMessage(),
                                new Object[0],
                                "catalog.product.error.not_found.message",
                                locale)));
    }
}

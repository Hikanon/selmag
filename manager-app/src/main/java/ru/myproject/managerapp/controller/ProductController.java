package ru.myproject.managerapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.myproject.managerapp.dto.UpdateProductDto;
import ru.myproject.managerapp.entity.Product;
import ru.myproject.managerapp.exception.BadRequestException;
import ru.myproject.managerapp.service.api.ProductService;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog/products/{productId:\\d+}")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product productAttribute(@PathVariable("productId") long productId) {
        return productService.findById(productId);
    }

    @GetMapping
    public String view() {
        return "catalog/products/product";
    }

    @GetMapping("/edit")
    public String editView(@PathVariable("productId") long productId,
                           Model model) {
        model.addAttribute("productId", productId);
        return "catalog/products/edit";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product,
                                UpdateProductDto updateProductDto,
                                Model model) {
        try {
            this.productService.updateProduct(product.id(), updateProductDto);
            return "redirect:/catalog/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", updateProductDto);
            model.addAttribute("errors", exception.getErrors());
            return "catalog/products/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.delete(product.id());
        return "redirect:/catalog/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}

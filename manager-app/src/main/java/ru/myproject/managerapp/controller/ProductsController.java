package ru.myproject.managerapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.myproject.managerapp.dto.NewProductDto;
import ru.myproject.managerapp.entity.Product;
import ru.myproject.managerapp.exception.BadRequestException;
import ru.myproject.managerapp.service.api.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog/products")
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/list")
    public String productsView(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "catalog/products/list";
    }

    @GetMapping("/create")
    public String createView() {
        return "catalog/products/create";
    }

    @PostMapping("create")
    public String createProduct(NewProductDto newProductDto,
                                Model model) {
        try {
            Product product = this.productService.createProduct(newProductDto);
            return "redirect:/catalog/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", newProductDto);
            model.addAttribute("errors", exception.getErrors());
            return "catalog/products/create";
        }
    }
}

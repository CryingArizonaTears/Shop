package com.andersenlab.shop.controller.all;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    public ProductController(@Qualifier("productService") IProductService productService) {
        this.productService = productService;
    }

    private final IProductService productService;

    @Logging
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @Logging
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        ProductDto product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

}

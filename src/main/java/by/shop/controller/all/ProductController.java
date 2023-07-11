package by.shop.controller.all;

import by.shop.annotation.Logging;
import by.shop.dto.ProductDto;
import by.shop.facade.ProductFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/products")
public class ProductController {

    ProductFacade productFacade;

    @Logging
    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> products = productFacade.getAll();
        return ResponseEntity.ok(products);
    }

    @Logging
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        ProductDto product = productFacade.getById(id);
        return ResponseEntity.ok(product);
    }

}

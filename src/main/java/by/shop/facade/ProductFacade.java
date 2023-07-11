package by.shop.facade;

import by.shop.dto.ProductDto;

import java.util.List;

public interface ProductFacade {
    List<ProductDto> getAll();

    ProductDto getById(Long id);

    ProductDto create(ProductDto productDto);

    ProductDto edit(ProductDto productDto);

    void delete(Long id);
}

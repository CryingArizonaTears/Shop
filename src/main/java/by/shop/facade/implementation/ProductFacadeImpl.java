package by.shop.facade.implementation;

import by.shop.annotation.Logging;
import by.shop.dto.ProductDto;
import by.shop.facade.ProductFacade;
import by.shop.model.Product;
import by.shop.modelMapperMethods.ExtendedModelMapper;
import by.shop.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {

    ProductService productService;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<ProductDto> getAll() {
        return modelMapper.mapList(productService.getAll(), ProductDto.class);
    }

    @Logging
    @Override
    public ProductDto getById(Long id) {
        return modelMapper.map(productService.getById(id), ProductDto.class);
    }

    @Logging
    @Override
    public ProductDto create(ProductDto productDto) {
        return modelMapper.map(productService.create(modelMapper.map(productDto, Product.class)), ProductDto.class);
    }

    @Logging
    @Override
    public ProductDto edit(ProductDto productDto) {
        return modelMapper.map(productService.edit(modelMapper.map(productDto, Product.class)), ProductDto.class);
    }

    @Logging
    @Override
    public void delete(Long id) {
        productService.delete(id);
    }
}

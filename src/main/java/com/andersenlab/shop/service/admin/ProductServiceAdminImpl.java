package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.ProductRepository;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.model.Product;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceAdminImpl implements ProductService {

    ProductRepository productRepository;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<ProductDto> getAll() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return modelMapper.mapList(products, ProductDto.class);
    }

    @Logging
    @Override
    public ProductDto getById(Long id) {
        return modelMapper.map(productRepository.findById(id), ProductDto.class);
    }

    @Logging
    @Override
    public void create(ProductDto productDto) {
        productRepository.save(modelMapper.map(productDto, Product.class));
    }

    @Logging
    @Override
    public void edit(ProductDto productDto) {
        ProductDto product = getById(productDto.getId());
        if (productDto.getProductType() != null) {
            product.setProductType(productDto.getProductType());
        }
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getExpDate() != null) {
            product.setExpDate(productDto.getExpDate());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getWarehouseDto() != null) {
            product.setWarehouseDto(productDto.getWarehouseDto());
        }
        productRepository.save(modelMapper.map(product, Product.class));
    }

    @Logging
    @Override
    public void delete(ProductDto productDto) {
        productRepository.delete(modelMapper.map(productDto, Product.class));
    }
}

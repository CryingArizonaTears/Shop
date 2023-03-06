package com.andersenlab.shop.service.user;

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
public class ProductServiceImpl implements ProductService {

    ProductRepository productDao;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<ProductDto> getAll() {
        List<Product> products = (List<Product>) productDao.findAll();
        return modelMapper.mapList(products, ProductDto.class);
    }

    @Logging
    @Override
    public ProductDto getById(Long id) {
        return modelMapper.map(productDao.findById(id), ProductDto.class);
    }

    @Logging
    @Override
    public void create(ProductDto productDto) {

    }

    @Logging
    @Override
    public void edit(ProductDto productDto) {

    }

    @Logging
    @Override
    public void delete(ProductDto productDto) {

    }
}

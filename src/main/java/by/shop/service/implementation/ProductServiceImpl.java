package by.shop.service.implementation;

import by.shop.annotation.Logging;
import by.shop.model.Product;
import by.shop.repository.ProductRepository;
import by.shop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Logging
    @Override
    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Logging
    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found "));
    }

    @Logging
    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Logging
    @Override
    public Product edit(Product product) {
        Product productFromRepo = getById(product.getId());
        if (product.getProductType() != null) {
            productFromRepo.setProductType(product.getProductType());
        }
        if (product.getName() != null) {
            productFromRepo.setName(product.getName());
        }
        if (product.getExpDate() != null) {
            productFromRepo.setExpDate(product.getExpDate());
        }
        if (product.getPrice() != null) {
            productFromRepo.setPrice(product.getPrice());
        }
        if (product.getWarehouse() != null) {
            productFromRepo.setWarehouse(product.getWarehouse());
        }
        return productRepository.save(productFromRepo);
    }

    @Logging
    @Override
    public void delete(Long id) {
        Product existingProduct = getById(id);
        productRepository.delete(existingProduct);
    }
}

package org.learning.productservice.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learning.productservice.Dto.ProductRequest;
import org.learning.productservice.Dto.ProductResponse;
import org.learning.productservice.Models.Product;
import org.learning.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {



    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved",product.getId());
    }


    public List<ProductResponse> getAllProducts() {
        List<Product> products= productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    public ProductResponse getProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::mapToProductResponse)
                .orElse(ProductResponse.builder().
                        name("not found").build());
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}

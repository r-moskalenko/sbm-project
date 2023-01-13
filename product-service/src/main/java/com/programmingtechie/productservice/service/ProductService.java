package com.programmingtechie.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.programmingtechie.productservice.dto.FlaskResponse;
import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final WebClient.Builder webClientBuilder;

    public ProductService(ProductRepository productRepository, WebClient.Builder webClientBuilder) {
        this.productRepository = productRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void createProduct(ProductRequest productRequest) throws JsonProcessingException {
        var flaskResponse = webClientBuilder.build().get()
                .uri("http://flask-service:5000/api/flask/hello")
                .retrieve()
                .bodyToMono(FlaskResponse.class)
                .block();

        flaskResponse = Optional.ofNullable(flaskResponse).orElse(new FlaskResponse("There is no response from flask"));

        Product product  = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription() + ", " + flaskResponse.message())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        logger.info("created new product = {}", product);
    }

    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();

        logger.info("all products: {}", Arrays.toString(products.toArray()));

        return products.stream()
                .map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}

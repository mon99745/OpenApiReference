package com.shop.shop.controller;

import com.shop.shop.domain.Product;
import com.shop.shop.dto.ProductMypriceRequestDto;
import com.shop.shop.domain.ProductRepository;
import com.shop.shop.dto.ProductRequestDto;
import com.shop.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // 꼭 필요한 요소(final) 자동 생성
@RestController // JSON 으로 응답하기 위한 RestController 라는 의미
public class ProductRestController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    // 등록된 전체 관심 상품 조회
    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // 관심 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        productRepository.save(product);
        return product;
    }

    // 최저가(관심가격) 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.update(id, requestDto);
    }
}



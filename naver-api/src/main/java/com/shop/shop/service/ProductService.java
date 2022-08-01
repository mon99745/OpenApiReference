package com.shop.shop.service;

import com.shop.shop.dto.ItemDto;
import com.shop.shop.domain.Product;
import com.shop.shop.dto.ProductMypriceRequestDto;
import com.shop.shop.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor // 꼭 필요한 요소(final) 자동 생성
@Service // 이 클래스가 서비스임을 알려줌
public class ProductService {
    // final 은 꼭 필요한 요소임을 명시하는 것, 값이 변경 될 수 없음
    private final ProductRepository productRepository;

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌, 자동으로 DB에 업데이트 됨
    public Long update(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        product.update(requestDto); // 관심 가격 변경
        return id;
    }

    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto); // 예약된 시간에 가격 변경
        return id;
    }
}

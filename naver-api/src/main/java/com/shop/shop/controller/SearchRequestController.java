package com.shop.shop.controller;

import com.shop.shop.dto.ItemDto;
import com.shop.shop.untils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor // 꼭 필요한 요소(final) 자동 생성
@RestController // JSON 으로 응답하기 위한 RestController 라는 의미
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;

    // Naver 쇼핑 API를 사용하여 검색된 상품 조회
    @GetMapping("/api/search")
    public List<ItemDto> getItems(@RequestParam String query) {
        String resultString = naverShopSearch.search(query);
        return naverShopSearch.fromJSONtoItems(resultString);
    }
}


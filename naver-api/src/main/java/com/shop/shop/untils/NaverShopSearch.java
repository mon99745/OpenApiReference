package com.shop.shop.untils;

import com.shop.shop.dto.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가
public class NaverShopSearch {
    // API를 통해 검색된 상품 목록 조회
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "ImzXuSduRnde_ql_zz1t");
        headers.add("X-Naver-Client-Secret", "rzzR1n14uo");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    // 검색된 상품 목록 데이터를 DTO로 변환
    public List<ItemDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items  = rjson.getJSONArray("items");
        List<ItemDto> ret = new ArrayList<>();

        for (int i=0; i<items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i);
            System.out.println(itemJson);
            ItemDto itemDto = new ItemDto(itemJson);
            ret.add(itemDto);
        }
        return ret;
    }
}



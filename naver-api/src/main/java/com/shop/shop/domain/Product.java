package com.shop.shop.domain;

import com.shop.shop.dto.ItemDto;
import com.shop.shop.dto.ProductMypriceRequestDto;
import com.shop.shop.dto.ProductRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // Lombok이 getter 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@Entity // 이 클래스는 DB의 테이블 역할
public class Product extends Timestamped { // 생성,수정 시간을 자동으로 생성하도록 상속받음

    // ID가 자동으로 생성 및 증가
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false) // 값이 반드시 존재해야 함
    private String title; // 상품명

    @Column(nullable = false)
    private String image; // 상품 이미지

    @Column(nullable = false)
    private String link; // 상품의 구매 링크

    @Column(nullable = false)
    private int lprice; // 상품 가격

    @Column(nullable = false)
    private int myprice; // 자신이 설정한 최저가(관심가격)

    // 관심 상품 생성 시
    public Product(ProductRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.myprice = 0;
    }

    // 최저가(관심가격) 변경 시
    public void update(ProductMypriceRequestDto requestDto) {
        this.myprice = requestDto.getMyprice();
    }

    // 예약된 시간에 가격 변경 시
    public void updateByItemDto(ItemDto itemDto) {
        this.lprice = itemDto.getLprice();
    }
}
package com.tomshop.shoppingjpa.dto;

import com.tomshop.shoppingjpa.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {


    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;
    //itemNm:상품명, createdBy:상품 등록자 아이디

    private String searchQuery = "";
    // 상품명기준검색 || 상품등록자 아이디 기준 -> 조회할 검색어 저장할 변수
    //searchBy가 어디냐에 다라 검색함.
}

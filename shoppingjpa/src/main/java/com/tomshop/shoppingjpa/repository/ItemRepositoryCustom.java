package com.tomshop.shoppingjpa.repository;

import com.tomshop.shoppingjpa.dto.ItemSearchDto;
import com.tomshop.shoppingjpa.dto.MainItemDto;
import com.tomshop.shoppingjpa.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    //상품 조회조건을 담고있는 itemSearchDto객체,
    //페이징 정보를 담고있는 pageable객체


    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}

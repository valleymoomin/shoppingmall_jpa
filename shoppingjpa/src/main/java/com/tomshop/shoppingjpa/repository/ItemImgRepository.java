package com.tomshop.shoppingjpa.repository;

import com.tomshop.shoppingjpa.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
    //상품 아이디, 매개변수 / 상품이미지아이디의 오름차순으로 가져오는 쿼리메소드


    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
    //상품 대표이미지를 찾는 쿼리 메소드 (구매이력페이지에서 상품대표이미지를 보여주기위해서..)

}

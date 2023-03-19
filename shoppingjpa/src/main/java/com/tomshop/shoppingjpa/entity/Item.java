package com.tomshop.shoppingjpa.entity;

import com.tomshop.shoppingjpa.constant.ItemSellStatus;
import com.tomshop.shoppingjpa.dto.ItemFormDto;
import com.tomshop.shoppingjpa.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {


    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //상품코드


    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name="price", nullable = false)
    private int price; //상품가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

/*    private LocalDateTime regTime; //등록시간

    private LocalDateTime updateTime; //수정 시간*/


    //상품을 업데이트
    //엔티티클래스에 비지니스로직을 추가한다면 조금 더 객체지향적으로 코딩할수있고,
    //코드를 재활용할수있다. 또한 데이터 변경포인트를 한군데서 관리할수있다.

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    //상품을 주문할경우 재고감소시키는 로직
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고수량: " + this.stockNumber +
                    ")");
        }
        this.stockNumber = restStock;
    }

}
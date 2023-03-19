package com.tomshop.shoppingjpa.service;

import com.tomshop.shoppingjpa.constant.ItemSellStatus;
import com.tomshop.shoppingjpa.dto.OrderDto;
import com.tomshop.shoppingjpa.entity.Item;
import com.tomshop.shoppingjpa.entity.Member;
import com.tomshop.shoppingjpa.entity.Order;
import com.tomshop.shoppingjpa.entity.OrderItem;
import com.tomshop.shoppingjpa.repository.ItemRepository;
import com.tomshop.shoppingjpa.repository.MemberRepository;
import com.tomshop.shoppingjpa.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember() {
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("order test")
    public void order() {
        Item item = saveItem();
        Member member = saveMember();


        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        //주문로직 호출결과 생성된주문번호를 orderId변수에 저장
        Long orderId = orderService.order(orderDto, member.getEmail());


        // 주문번호를 이용하여 저장된 주문정보를 조회
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems =
                order.getOrderItems();


        int totalPrice = orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());


    }
}
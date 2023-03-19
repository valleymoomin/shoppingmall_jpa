package com.tomshop.shoppingjpa.entity;

import com.tomshop.shoppingjpa.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Table(name= "orders")
@Entity
public class Order extends BaseEntity{

    @Id @GeneratedValue
    @Column(name= "order_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true
    ,fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();


/*
    private LocalDateTime regTime;

    private LocalDateTime updateTime;*/


    //OrderItem 주문상품객체 -> 주문객체
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        } //장바구니 -> 한번에 여러개상품 주문가능. 여러개주문상품을 담을수있도록,

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    //총 주문금액 구하기.
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}

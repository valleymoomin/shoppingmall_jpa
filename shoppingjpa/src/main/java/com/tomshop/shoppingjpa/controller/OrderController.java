package com.tomshop.shoppingjpa.controller;

import com.tomshop.shoppingjpa.dto.OrderDto;
import com.tomshop.shoppingjpa.dto.OrderHistDto;
import com.tomshop.shoppingjpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class OrderController {


    private final OrderService orderService;

    @PostMapping(value = "/order")
    public @ResponseBody
    ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }


        String email = principal.getName();
        //로그인유저의 정보를 얻기위해서 principal객체를 파라미터로 넘겨줄경우, 직접접근이가능.
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }


    //구매이력 조회
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        //현재 로그인한 회원 (이메일, 페이징객체를 파라미터로 전달) -> 화면에 전달한 주문목록데이터를 리턴값으로 받음.
        Page<OrderHistDto> ordersHistDtoList =
                orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "order/orderHist";
    }
}

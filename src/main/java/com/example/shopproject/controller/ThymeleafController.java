package com.example.shopproject.controller;

import com.example.shopproject.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {
    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        model.addAttribute("data","타임리프 예제입니다");
        return "thymeleafEx/thymeleafEx01";
    }
    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model){
        ItemDto itemDto=new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트상품 1번");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto",itemDto);
        return "thymeleafEx/thymeleafEx02";
    }
    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model){
        List<ItemDto> list=new ArrayList<>();

        for (int i=0; i<5; i++){
            ItemDto itemDto=new ItemDto();itemDto.setItemDetail("상품 상세 설명"+i);
            itemDto.setItemNm("테스트상품 1번"+i);
            itemDto.setPrice(10000+i);
            itemDto.setRegTime(LocalDateTime.now());

            list.add(itemDto);
        }
        model.addAttribute("list",list);
        return "thymeleafEx/thymeleafEx03";
    }
    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model){
        List<ItemDto> list=new ArrayList<>();

        for (int i=0; i<5; i++){
            ItemDto itemDto=new ItemDto();itemDto.setItemDetail("상품 상세 설명"+i);
            itemDto.setItemNm("테스트상품 1번"+i);
            itemDto.setPrice(10000+i);
            itemDto.setRegTime(LocalDateTime.now());

            list.add(itemDto);
        }
        model.addAttribute("list",list);
        return "thymeleafEx/thymeleafEx04";
    }
}

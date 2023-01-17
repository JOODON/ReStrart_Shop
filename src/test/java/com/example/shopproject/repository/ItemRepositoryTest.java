package com.example.shopproject.repository;

import com.example.shopproject.constant.ItemSellStatus;
import com.example.shopproject.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item=new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem=itemRepository.save(item);

        System.out.println(savedItem.toString());
    }
    public void createItemList(){
        for (int i=1; i<=10; i++){
            Item item=new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem=itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("상품 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> items=itemRepository.findByItemNm("테스트상품1");
        for (Item item : items){
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("상품 조회 테스트")
    public void findByItemNmOrItemDetailsTest(){
        this.createItemList();
        List<Item> items=itemRepository.findByItemNmOrItemDetail("테스트상품1","테스트 상품 상세 설명5");
        for (Item item : items){
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> items=itemRepository.findByPriceLessThan(10005);
        for (Item item : items){
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemList();
        List<Item> itemList=itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("쿼리를 이용한 상품 조회 테스트")
    public void findByItemDetailsTest(){
        this.createItemList();
        List<Item> itemList=itemRepository.findByItemDetail("테스트 상품 설명");
        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }
}
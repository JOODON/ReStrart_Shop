package com.example.shopproject.repository;

import com.example.shopproject.constant.ItemSellStatus;
import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;



@SpringBootTest
@TestPropertySource(locations = "classpath:application.test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;//양속성 컨텐츠를 사용하기 위하여 빈을 주입해줌

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
        item.setUpDateTime(LocalDateTime.now());

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
            item.setUpDateTime(LocalDateTime.now());

            Item savedItem=itemRepository.save(item);
        }
    }
    public void createItemList2(){
        for (int i=1; i<=5; i++){
            Item item=new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpDateTime(LocalDateTime.now());

            Item savedItem=itemRepository.save(item);
        }
        for (int i=6; i<=10; i++){
            Item item=new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpDateTime(LocalDateTime.now());

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
    @Test
    @DisplayName("쿼리dsl 조회테스트1")
    public void queryDslTest(){
        this.createItemList();
        JPAQueryFactory queryFactory=new JPAQueryFactory(em);//쿼리를 동적으로 생성 파라미터는 Em객체를 주입
        QItem qItem=QItem.item;//쿼리 객체를 생성해줌
        JPAQuery<Item> query=queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%"+"테스트 상품 상세 설명서"+"%")).orderBy(qItem.price.desc());

        List<Item> itemList=query.fetch();//쿼리문이 실행 되는 부분

        for (Item item : itemList){
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("상품 쿼리dsl 조회 테스트2")
    public void queryDslTest2(){
        this.createItemList2();

        BooleanBuilder booleanBuilder=new BooleanBuilder();//쿼리에 들어갈수 있는 조건을 만들어주는 녀석
        QItem qItem=QItem.item;

        String itemDetail="테스트 상세 설명";
        int price =10003;
        String itemSellStat="SELL";

        booleanBuilder.and(qItem.itemDetail.like("%"+ itemDetail +"%"));
        booleanBuilder.and(qItem.price.gt(price));

        if (StringUtils.equals(itemSellStat,ItemSellStatus.SELL)){
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable= PageRequest.of(0,5);
        Page<Item> itemPagingResult=itemRepository.findAll((Predicate) booleanBuilder, (org.springframework.data.domain.Pageable) pageable);

        System.out.println("Total elements:"+itemPagingResult.getTotalElements());
        List<Item> resultItemList=itemPagingResult.getContent();

        for(Item resultItem:resultItemList){
            System.out.println(resultItem.toString());
        }
    }
}
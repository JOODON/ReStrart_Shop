package com.example.shopproject.dto;

import com.example.shopproject.constant.ItemSellStatus;
import com.example.shopproject.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수값 입니다")
    private String itemNm;

    @NotNull(message = "상품값은 필수값 입니다")
    private Integer price;

    @NotBlank(message = "이름은 필수값 입니다")
    private String itemDetail;

    @NotBlank(message = "재고는 필수값 입니다")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList=new ArrayList<>();
    //싱픔 저장후 수정할떄 상품이미지 정보를 저장하는 리스트
    private List<Long> itemImgIds=new ArrayList<>();
    //상품의 아이디 이미지를 저장하는 리스트
    private static ModelMapper modelMapper=new ModelMapper();
    //데이터와 객체를 복사해주는 메소드
    public Item createItem(){
        return modelMapper.map(this,Item.class);
    }
    public static ItemFormDto of(Item item){
        return modelMapper.map(item,ItemFormDto.class);
    }
}

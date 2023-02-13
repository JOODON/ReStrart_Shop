package com.example.shopproject.service;

import com.example.shopproject.dto.ItemFormDto;
import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.ItemImg;
import com.example.shopproject.repository.ItemImgRepository;
import com.example.shopproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList)throws  Exception{
        //상품 등록
        Item item=itemFormDto.createItem();
        itemRepository.save(item);

        for (int i=0; i<itemImgFileList.size(); i++){
            ItemImg itemImg=new ItemImg();
            itemImg.setItem(item);
            if (i == 0){
                itemImg.setRepimgYn("Y");
            }
            else {
                itemImg.setRepimgYn("N");
                itemImgService.saveItemImg(itemImg,itemImgFileList.get(i));
            }
        }
        return item.getId();
    }
}

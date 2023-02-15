package com.example.shopproject.service;

import com.example.shopproject.dto.ItemFormDto;
import com.example.shopproject.dto.ItemImgDto;
import com.example.shopproject.dto.ItemSearchDto;
import com.example.shopproject.dto.MainItemDto;
import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.ItemImg;
import com.example.shopproject.repository.ItemImgRepository;
import com.example.shopproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImg> itemImgList=itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList=new ArrayList<>();
        for (ItemImg itemImg:itemImgList){
            ItemImgDto itemImgDto=ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item=itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto=ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;

    }
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
            }
            itemImgService.saveItemImg(itemImg,itemImgFileList.get(i));
        }
        return item.getId();
    }
    public Long updateItem(ItemFormDto itemFormDto,List<MultipartFile> itemImgFileList) throws Exception{
        Item item=itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityExistsException::new);

        item.updateTime(itemFormDto);

        List<Long> itemImgIds=itemFormDto.getItemImgIds();

        for (int i=0; i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i),itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto,Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto,pageable);
    }
}

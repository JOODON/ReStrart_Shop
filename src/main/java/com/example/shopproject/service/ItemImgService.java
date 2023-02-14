package com.example.shopproject.service;

import com.example.shopproject.entity.ItemImg;
import com.example.shopproject.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor//final 필드에 자동으로 생성자를 만들어줌
@Transactional
public class ItemImgService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName=itemImgFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";
        if(!StringUtils.isEmpty(oriImgName)){
            imgName=fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
            imgUrl="/images/item/"+imgName;
        }
        //상품 이미지 저장
        itemImg.updateItemImg(oriImgName,imgName,imgUrl);
        itemImgRepository.save(itemImg);
    }
    public void updateItemImg(Long itemImgId,MultipartFile itemImgFile) throws Exception{
        if (!itemImgFile.isEmpty()){
            //Empty 비어있다
            ItemImg savedItemImg=itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);
            //기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }
            String oriImgName=itemImgFile.getOriginalFilename();
            String imgName=fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
            String imgUrl="/images/item/"+imgName;
            savedItemImg.updateItemImg(oriImgName,imgName,imgUrl);
        }
    }
}

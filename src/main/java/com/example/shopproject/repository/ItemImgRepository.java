package com.example.shopproject.repository;

import com.example.shopproject.entity.Item;
import com.example.shopproject.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {
    List<ItemImg> findByItemIdOOrderByIdAsc(Long id);
}

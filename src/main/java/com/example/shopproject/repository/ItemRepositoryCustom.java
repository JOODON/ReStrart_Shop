package com.example.shopproject.repository;

import com.example.shopproject.dto.ItemSearchDto;
import com.example.shopproject.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}

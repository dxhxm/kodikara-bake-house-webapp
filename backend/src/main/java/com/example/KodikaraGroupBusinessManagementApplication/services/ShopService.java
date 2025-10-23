package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.ShopDTO;

import java.util.List;

public interface ShopService {
    ShopDTO save(ShopDTO shopDTO);
    ShopDTO getShopById(Integer shopId);
    List<ShopDTO> getAllShops();
    void deleteShop(Integer shopId);
    List<ShopDTO> searchShops(String query);
}
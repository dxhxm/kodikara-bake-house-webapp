package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.ShopDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.ShopRepository;
import com.example.KodikaraGroupBusinessManagementApplication.model.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Transactional(readOnly = true)
    public List<ShopDTO> getAllShops() {
        return shopRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ShopDTO toDto(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setShopId(shop.getShopId());
        dto.setName(shop.getName());
        dto.setLocation(shop.getLocation());
        return dto;
    }
}
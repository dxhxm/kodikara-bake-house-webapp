package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.ShopDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.ShopRepository;
import com.example.KodikaraGroupBusinessManagementApplication.exception.ResourceNotFoundException;
import com.example.KodikaraGroupBusinessManagementApplication.model.Shop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public ShopDTO save(ShopDTO shopDTO) {
        if (shopDTO.getName() == null || shopDTO.getName().isEmpty() ||
            shopDTO.getLocation() == null || shopDTO.getLocation().isEmpty() ||
            shopDTO.getContactDetails() == null || shopDTO.getContactDetails().isEmpty() ||
            shopDTO.getOwnerName() == null || shopDTO.getOwnerName().isEmpty()) {
            throw new IllegalArgumentException("All fields are required.");
        }

        if (!shopDTO.getContactDetails().matches("\\d{1,15}")) {
            throw new IllegalArgumentException("Contact number must contain only digits (max 15).");
        }

        Shop shop = toEntity(shopDTO);
        return toDto(shopRepository.save(shop));
    }

    @Override
    @Transactional(readOnly = true)
    public ShopDTO getShopById(Integer shopId) {
        return shopRepository.findById(shopId)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopDTO> getAllShops() {
        return shopRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteShop(Integer shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new ResourceNotFoundException("Shop not found.");
        }
        shopRepository.deleteById(shopId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopDTO> searchShops(String query) {
        return shopRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCaseOrOwnerNameContainingIgnoreCase(query, query, query)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ShopDTO toDto(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setShopId(shop.getShopId());
        dto.setName(shop.getName());
        dto.setLocation(shop.getLocation());
        dto.setContactDetails(shop.getContactDetails());
        dto.setOwnerName(shop.getOwnerName());
        return dto;
    }

    private Shop toEntity(ShopDTO dto) {
        Shop shop = new Shop();
        shop.setShopId(dto.getShopId());
        shop.setName(dto.getName());
        shop.setLocation(dto.getLocation());
        shop.setContactDetails(dto.getContactDetails());
        shop.setOwnerName(dto.getOwnerName());
        return shop;
    }
}

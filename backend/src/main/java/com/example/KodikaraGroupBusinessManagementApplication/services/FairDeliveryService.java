package com.example.KodikaraGroupBusinessManagementApplication.services;


import com.example.KodikaraGroupBusinessManagementApplication.DTO.FairDeliveryItemDTO;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.FairDeliveryRequestDTO;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.FairDeliveryResponseDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.FairDeliveryItemRepository;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.FairDeliveryRepository;
import com.example.KodikaraGroupBusinessManagementApplication.services.IdGenerator; // Corrected import
import com.example.KodikaraGroupBusinessManagementApplication.model.FairDelivery;
import com.example.KodikaraGroupBusinessManagementApplication.model.FairDeliveryItem;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // Added for transactional operations

import java.util.ArrayList;
import java.util.List;

@Service
public class FairDeliveryService {
    private final FairDeliveryRepository deliveryRepo;
    private final FairDeliveryItemRepository itemRepo;

    public FairDeliveryService(FairDeliveryRepository deliveryRepo, FairDeliveryItemRepository itemRepo) {
        this.deliveryRepo = deliveryRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional // Ensure atomicity for delivery and items
    public FairDeliveryResponseDTO createDelivery(FairDeliveryRequestDTO dto) {
        FairDelivery delivery = new FairDelivery();
        delivery.setDeliveryId(IdGenerator.generate("FD")); // Generate ID
        delivery.setVehicleId(dto.getVehicleId());
        delivery.setDriverId(dto.getDriverId());
        delivery.setFairName(dto.getFairName());
        delivery.setDeliveryDate(dto.getDeliveryDate()); // Corrected access
        delivery.setStatus("OUT");
        FairDelivery savedDelivery = deliveryRepo.save(delivery);

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<FairDeliveryItem> itemsToSave = new ArrayList<>();
            for (FairDeliveryItemDTO itemDto : dto.getItems()) {
                FairDeliveryItem item = new FairDeliveryItem();
                item.setItemId(savedDelivery.getDeliveryId() + "_" + itemDto.getProId()); // Use generated delivery ID
                item.setDeliveryId(savedDelivery.getDeliveryId());
                item.setProId(itemDto.getProId());
                item.setQtySent(itemDto.getQtySent());
                item.setUnitPrice(itemDto.getUnitPrice());
                item.setQtyRemaining(0); // Assuming initial remaining is 0 or needs to be calculated
                itemsToSave.add(item);
            }
            itemRepo.saveAll(itemsToSave);
        }

        return new FairDeliveryResponseDTO(
                savedDelivery.getDeliveryId(),
                savedDelivery.getVehicleId(),
                savedDelivery.getDriverId(),
                savedDelivery.getFairName(),
                savedDelivery.getDeliveryDate(),
                savedDelivery.getStatus()
        );
    }
}
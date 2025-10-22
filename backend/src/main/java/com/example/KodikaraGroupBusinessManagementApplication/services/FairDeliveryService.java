package com.example.KodikaraGroupBusinessManagementApplication.services;


import com.example.KodikaraGroupBusinessManagementApplication.DTO.FairDeliveryRequestDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.FairDeliveryItemRepository;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.FairDeliveryRepository;
import com.example.KodikaraGroupBusinessManagementApplication.model.FairDelivery;
import com.example.KodikaraGroupBusinessManagementApplication.model.FairDeliveryItem;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FairDeliveryService {
    private final FairDeliveryRepository deliveryRepo;
    private final FairDeliveryItemRepository itemRepo;

    public FairDeliveryService(FairDeliveryRepository deliveryRepo, FairDeliveryItemRepository itemRepo) {
        this.deliveryRepo = deliveryRepo;
        this.itemRepo = itemRepo;
    }

    public void createDelivery(FairDeliveryRequestDTO dto) {
        FairDelivery delivery = new FairDelivery();
        delivery.setDeliveryId(dto.deliveryId);
        delivery.setVehicleId(dto.vehicleId);
        delivery.setDriverId(dto.driverId);
        delivery.setFairName(dto.fairName);
        delivery.setDeliveryDate(LocalDate.parse(dto.deliveryDate));
        delivery.setStatus("OUT");
        deliveryRepo.save(delivery);

        dto.items.forEach(i -> {
            FairDeliveryItem item = new FairDeliveryItem();
            item.setItemId(dto.deliveryId + "_" + i.proId);
            item.setDeliveryId(dto.deliveryId);
            item.setProId(i.proId);
            item.setQtySent(i.qtySent);
            item.setUnitPrice(i.unitPrice);
            item.setQtyRemaining(0);
            itemRepo.save(item);
        });
    }
}
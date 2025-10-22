package com.example.KodikaraGroupBusinessManagementApplication.Controller;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.FairDeliveryRequestDTO;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.FairDeliveryResponseDTO;
import com.example.KodikaraGroupBusinessManagementApplication.services.FairDeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fair-deliveries")
public class FairDeliveryController {
    private final FairDeliveryService service;
    public FairDeliveryController(FairDeliveryService service){
        this.service = service;
    }
    @PostMapping // Simplified mapping
    public ResponseEntity<FairDeliveryResponseDTO> create(@RequestBody FairDeliveryRequestDTO dto){
        FairDeliveryResponseDTO createdDelivery = service.createDelivery(dto);
        return ResponseEntity.status(201).body(createdDelivery);
    }
}

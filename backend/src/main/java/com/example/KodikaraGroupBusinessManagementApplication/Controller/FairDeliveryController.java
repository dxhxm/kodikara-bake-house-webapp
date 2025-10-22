package com.example.KodikaraGroupBusinessManagementApplication.Controller;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.FairDeliveryRequestDTO;
import com.example.KodikaraGroupBusinessManagementApplication.model.FairDelivery;
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
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FairDeliveryRequestDTO dto){
        service.createDelivery(dto);
        return ResponseEntity.ok("Fair Delivery Created");
    }
}

package com.example.KodikaraGroupBusinessManagementApplication.Controller;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.ShopDTO;
import com.example.KodikaraGroupBusinessManagementApplication.services.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ResponseEntity<ShopDTO> createShop(@RequestBody ShopDTO shopDTO) {
        ShopDTO savedShop = shopService.save(shopDTO);
        return new ResponseEntity<>(savedShop, HttpStatus.CREATED);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable Integer shopId) {
        ShopDTO shopDTO = shopService.getShopById(shopId);
        return ResponseEntity.ok(shopDTO);
    }

    @GetMapping
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        List<ShopDTO> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Integer shopId, @RequestBody ShopDTO shopDTO) {
        shopDTO.setShopId(shopId);
        ShopDTO updatedShop = shopService.save(shopDTO);
        return ResponseEntity.ok(updatedShop);
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer shopId) {
        shopService.deleteShop(shopId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShopDTO>> searchShops(@RequestParam String query) {
        List<ShopDTO> shops = shopService.searchShops(query);
        return ResponseEntity.ok(shops);
    }
}
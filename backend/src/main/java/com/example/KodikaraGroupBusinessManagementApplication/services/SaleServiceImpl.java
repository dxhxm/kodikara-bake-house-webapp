package com.example.KodikaraGroupBusinessManagementApplication.services;

import com.example.KodikaraGroupBusinessManagementApplication.DTO.SaleDTO;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.SaleItemResponse;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.SaleRequestDTO;
import com.example.KodikaraGroupBusinessManagementApplication.DTO.SaleResponseDTO;
import com.example.KodikaraGroupBusinessManagementApplication.Repo.*;
import com.example.KodikaraGroupBusinessManagementApplication.exception.ResourceNotFoundException;
import com.example.KodikaraGroupBusinessManagementApplication.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ShopRepository shopRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final VehicleRepository vehicleRepository;
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO dto) {
        Shop shop = shopRepository.findByShopName(dto.getShopName())
                .orElseGet(() -> {
                    Shop newShop = new Shop();
                    newShop.setShopId(IdGenerator.generate("SHOP"));
                    newShop.setShopName(dto.getShopName());
                    newShop.setOwnerName(dto.getOwnerName());
                    newShop.setContactNo(dto.getContactNo());
                    newShop.setAddress("Default Address");
                    return shopRepository.save(newShop);
                });

        Vehicle vehicle = vehicleRepository.findByVehicleNo(dto.getVehicleNo())
                .orElseGet(() -> {
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setVehicleId(IdGenerator.generate("VEH"));
                    newVehicle.setVehicleNo(dto.getVehicleNo());
                    newVehicle.setDriverName(dto.getDriverName());
                    newVehicle.setVehicleType("DELIVERY");
                    return vehicleRepository.save(newVehicle);
                });

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleDetail> saleDetails = new ArrayList<>();

        Sale sale = new Sale();
        sale.setSaleId(IdGenerator.saleId());
        sale.setShop(shop);
        sale.setVehicle(vehicle);
        sale.setSaleDate(LocalDate.now());
        sale.setPaymentMethod("CASH");

        for (SaleDTO item : dto.getItems()) {
            Product product = productRepository.findByName(item.getProductName())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.getProductName()));

            BigDecimal subtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setSdetailId(IdGenerator.generate("SD")); // Assign ID here
            saleDetail.setSale(sale);
            saleDetail.setProduct(product);
            saleDetail.setQty(item.getQuantity());
            saleDetail.setSubTot(subtotal);
            saleDetails.add(saleDetail);
        }
        sale.setTotalAmount(totalAmount);
        sale.setSaleDetails(saleDetails);

        Sale savedSale = saleRepository.save(sale);
        saleDetailRepository.saveAll(saleDetails);

        return convertToResponseDTO(savedSale);
    }

    @Override
    public SaleResponseDTO getSaleById(String saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found: " + saleId));

        return convertToResponseDTO(sale);
    }

    @Override
    public List<SaleResponseDTO> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        return convertToResponseDTOList(sales);
    }

    @Override
    public void deleteSale(String saleId) {
        if (!saleRepository.existsById(saleId)) {
            throw new ResourceNotFoundException("Sale not found: " + saleId);
        }
        saleRepository.deleteById(saleId);
    }
    @Override
    public List<SaleResponseDTO> getSaleByDate(LocalDate date){
        List<Sale> sales = saleRepository.findBySaleDate(date);
        if(sales.isEmpty()){
            throw new ResourceNotFoundException("Sale not found: "+date);
        }
        return convertToResponseDTOList(sales);
    }
    @Override
    public List<SaleResponseDTO> getSaleByDateRange(LocalDate startDate,LocalDate endDate){
        List<Sale> sales =saleRepository.findBySaleDateBetween(startDate,endDate);
        if(sales.isEmpty()){
            throw new ResourceNotFoundException("Sale not found between: "+startDate+" "+ endDate+" ");
        }
        return convertToResponseDTOList(sales);
    }
    @Override
    @Transactional
    public void deleteSaleByDate(LocalDate date){
        if(!saleRepository.existsBySaleDate(date)){
            throw new ResourceNotFoundException("Sale not found: "+date);
        }
        saleRepository.deleteBySaleDate(date);
    }
    private SaleResponseDTO convertToResponseDTO(Sale sale){
        List<SaleItemResponse> items =new ArrayList<>();
        if(sale.getSaleDetails() !=null){
            for(SaleDetail detail : sale.getSaleDetails()){
                items.add(new SaleItemResponse(
                        detail.getProduct().getName(),
                        detail.getQty(),
                        detail.getSubTot()
                ));
            }
        }
        return new SaleResponseDTO(
                sale.getSaleId(),
                sale.getShop().getShopName(),
                sale.getShop().getOwnerName(),
                sale.getShop().getContactNo(),
                sale.getVehicle().getDriverName(),
                sale.getVehicle().getVehicleNo(),
                items,
                sale.getTotalAmount(),
                sale.getSaleDate()
        );
    }
    private List<SaleResponseDTO> convertToResponseDTOList(List<Sale> sales){
        return sales.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }
}

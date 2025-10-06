package com.example.KodikaraGroupBusinessManagementApplication.Repo;

import com.example.KodikaraGroupBusinessManagementApplication.model.PriceList;
import com.example.KodikaraGroupBusinessManagementApplication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Integer> {
    Optional<PriceList> findByProductAndDate(Product product, LocalDate date);
    Optional<PriceList> findFirstByProductOrderByDateDesc(Product product);
}
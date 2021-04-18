package com.example.servingwebcontent;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

  List<Coin> findByPrice(String price);

  Coin findById(long id);
}

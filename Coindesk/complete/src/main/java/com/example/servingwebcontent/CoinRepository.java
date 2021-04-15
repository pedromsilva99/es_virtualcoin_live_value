package com.example.servingwebcontent;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CoinRepository extends CrudRepository<Coin, Long> {

  List<Coin> findByPrice(String price);

  Coin findById(long id);
}

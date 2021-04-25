package com.example.servingwebcontent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
public class ServingWebContentApplication {

    // private static final Logger log = LoggerFactory.getLogger(ServingWebContentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    // @Bean
    // public CommandLineRunner demo(CoinRepository repository) {
    //   return (args) -> {
    //     // Save a few coins
    //      repository.save(new Coin("Bitcoin", "33500", "12-3-2020"));
    //      repository.save(new Coin("Bitcoin", "34500", "13-3-2020"));
    //      repository.save(new Coin("Bitcoin", "34300", "14-3-2020"));
    //      repository.save(new Coin("Bitcoin", "32500", "15-3-2020"));
    //      repository.save(new Coin("Bitcoin", "33500", "16-3-2020"));
    //
    //     // Fetch all coins
    //     log.info("Coins found with findAll():");
    //     log.info("---------------------------");
    //     for (Coin coin : repository.findAll()) {
    //       log.info(coin.toString());
    //     }
    //     log.info("");
    //
    //     // Fetch 1 coin
    //     // Coin coin = repository.findById(1L);
    //     // log.info("Coin found with findById(1L):");
    //     // log.info("-----------------------------");
    //     // log.info(coin.toString());
    //     // log.info("");
    //
    //     // Fetch coins by price
    //     log.info("Customer found with findByPrice('33500'):");
    //     log.info("-----------------------------------------");
    //     repository.findByPrice("33500").forEach(btc -> {
    //       log.info(btc.toString());
    //     });
    //     log.info("");
    //   };
    // }
}

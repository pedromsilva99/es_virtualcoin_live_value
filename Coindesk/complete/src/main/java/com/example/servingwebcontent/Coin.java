package com.example.servingwebcontent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coin {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String name;
  private String price;
  private String date;

  protected Coin() {}

  public Coin(String name, String price, String date) {
    this.name = name;
    this.price = price;
    this.date = date;
  }

  @Override
  public String toString() {
    return String.format("Name: %s, price: %s, date: %s", name, price, date);
  }

  public String getName() {
    return name;
  }

  public String getPrice() {
    return price;
  }

  public String getDate() {
    return date;
  }
}

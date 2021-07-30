package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CityDTO   {
  @JsonProperty("cityId")
  private Long cityId;

  @JsonProperty("name")
  private String name;

   public CityDTO(Long cityId, String name) {
      this.cityId = cityId;
      this.name = name;
   }

   public CityDTO() {
   }

   public CityDTO cityId(Long cityId) {
    this.cityId = cityId;
    return this;
  }

   public Long getCityId() {
      return this.cityId;
   }

   public String getName() {
      return this.name;
   }

   public void setCityId(Long cityId) {
      this.cityId = cityId;
   }

   public void setName(String name) {
      this.name = name;
   }

}


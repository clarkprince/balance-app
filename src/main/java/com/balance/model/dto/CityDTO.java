package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO   {
  @JsonProperty("cityId")
  private Long cityId;

  @JsonProperty("name")
  private String name;

  public CityDTO cityId(Long cityId) {
    this.cityId = cityId;
    return this;
  }

}


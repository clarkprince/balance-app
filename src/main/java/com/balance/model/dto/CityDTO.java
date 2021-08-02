package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("City")
public class CityDTO {
   @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
   @JsonProperty("cityId")
   private Long cityId;

   @ApiModelProperty(example = "Amsterdam")
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


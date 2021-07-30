package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityDTO {
   @JsonProperty("communityId")
   private Long communityId;

   @JsonProperty("name")
   private String name;

   @JsonProperty("cityId")
   private Long cityId;

   public CommunityDTO(Long communityId, String name, Long cityId) {
      this.communityId = communityId;
      this.name = name;
      this.cityId = cityId;
   }

   public CommunityDTO() {
   }

   public Long getCommunityId() {
      return this.communityId;
   }

   public String getName() {
      return this.name;
   }

   public Long getCityId() {
      return this.cityId;
   }

   public void setCommunityId(Long communityId) {
      this.communityId = communityId;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setCityId(Long cityId) {
      this.cityId = cityId;
   }
}


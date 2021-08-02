package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Community")
public class CommunityDTO {
   @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
   @JsonProperty("communityId")
   private Long communityId;

   @ApiModelProperty(example = "demo community")
   @JsonProperty("name")
   private String name;

   @ApiModelProperty(example = "1")
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


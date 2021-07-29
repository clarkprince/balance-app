package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityDTO {
   @JsonProperty("communityId")
   private Long communityId;

   @JsonProperty("name")
   private String name;

   @JsonProperty("cityId")
   private Long cityId;

}


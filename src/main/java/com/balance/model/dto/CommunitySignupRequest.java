package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySignupRequest   {
  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("communityId")
  private Long communityId;
}


package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CommunitySignupRequest   {
  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("communityId")
  private Long communityId;

   public CommunitySignupRequest(Long userId, Long communityId) {
      this.userId = userId;
      this.communityId = communityId;
   }

   public CommunitySignupRequest() {
   }

   public Long getUserId() {
      return this.userId;
   }

   public Long getCommunityId() {
      return this.communityId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public void setCommunityId(Long communityId) {
      this.communityId = communityId;
   }
}


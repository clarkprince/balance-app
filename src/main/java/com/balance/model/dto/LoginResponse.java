package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel("Authorization token")
public class LoginResponse {
   @JsonProperty("token")
   String token;

   public LoginResponse(String token) {
      this.token = token;
   }

   public LoginResponse() {
   }
}

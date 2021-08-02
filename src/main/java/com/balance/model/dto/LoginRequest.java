package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Login Request")
public class LoginRequest {

   @ApiModelProperty(example = "john_doe123")
   @JsonProperty("username")
   private String username;

   @ApiModelProperty(example = "HXbk5Zk6")
   @JsonProperty("password")
   private String password;

   public LoginRequest(String username, String password) {
      this.username = username;
      this.password = password;
   }

   public LoginRequest() {
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.password;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public void setPassword(String password) {
      this.password = password;
   }

}


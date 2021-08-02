package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Signup Request")
public class SignupRequest {

   @ApiModelProperty(example = "John")
   @JsonProperty("firstName")
   private String firstName;

   @ApiModelProperty(example = "Doe")
   @JsonProperty("lastName")
   private String lastName;

   @ApiModelProperty(example = "john.doe@email.com")
   @JsonProperty("email")
   private String email;

   @ApiModelProperty(example = "john_doe123")
   @JsonProperty("username")
   private String username;

   @ApiModelProperty(example = "HXbk5Zk6")
   @JsonProperty("password")
   private String password;

   public SignupRequest(String firstName, String lastName, String username, String password, String roles) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.username = username;
      this.password = password;
   }

   public SignupRequest() {
   }

   public String getFirstName() {
      return this.firstName;
   }

   public String getLastName() {
      return this.lastName;
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.password;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public void setPassword(String password) {
      this.password = password;
   }

}


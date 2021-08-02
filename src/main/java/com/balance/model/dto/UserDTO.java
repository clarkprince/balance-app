package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("User")
public class UserDTO {
   @ApiModelProperty(example = "John")
   @JsonProperty("firstName")
   private String firstName;

   @ApiModelProperty(example = "Doe")
   @JsonProperty("lastName")
   private String lastName;

   @ApiModelProperty(example = "john_doe123")
   @JsonProperty("username")
   private String username;

   @ApiModelProperty(example = "john.doe@email.com")
   @JsonProperty("email")
   private String email;

   @ApiModelProperty(example = "true")
   @JsonProperty("active")
   private boolean active;

   @JsonProperty("communities")
   private List<CommunityDTO> communities;

   public UserDTO(String firstName, String lastName, String username, String email, boolean active, String roles, List<CommunityDTO> communities) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.username = username;
      this.email = email;
      this.active = active;
      this.communities = communities;
   }

   public UserDTO() {
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

   public List<CommunityDTO> getCommunities() {
      return this.communities;
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

   public void setCommunities(List<CommunityDTO> communities) {
      this.communities = communities;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }
}

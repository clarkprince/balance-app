package com.balance.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
   @JsonProperty("firstName")
   private String firstName;

   @JsonProperty("lastName")
   private String lastName;

   @JsonProperty("username")
   private String username;

   @JsonProperty("roles")
   private String roles;

   @JsonProperty("communities")
   private List<CommunityDTO> communities;

}

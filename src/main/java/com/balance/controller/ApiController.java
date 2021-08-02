package com.balance.controller;

import com.balance.model.Community;
import com.balance.model.VerificationToken;
import com.balance.model.dto.*;
import com.balance.service.CityService;
import com.balance.service.CommunityService;
import com.balance.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class ApiController {

   private static final Logger log = LoggerFactory.getLogger(ApiController.class);
   private final UserService userService;
   private final CommunityService communityService;
   private final CityService cityService;

   public ApiController(UserService userService, CommunityService communityService, CityService cityService) {
      this.userService = userService;
      this.communityService = communityService;
      this.cityService = cityService;
   }

   @ApiOperation(value = "Sign Up User", nickname = "apiSignupPost", tags = "User")
   @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
   @RequestMapping(value = "/api/signup", consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"}, method = RequestMethod.POST)
   public ResponseEntity<VerificationToken> apiSignup(@Valid @RequestBody UserDTO userDTO) {
      log.info("Adding new user...");
      VerificationToken verificationToken = userService.signupAndSendVerificationEmail(userDTO);
      return ResponseEntity.ok(verificationToken);
   }


   @ApiOperation(value = "Create Community", nickname = "apiCommunityPost", authorizations = @Authorization(value = "Bearer"), tags = "Community")
   @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
   @RequestMapping(value = "/api/community", consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"}, method = RequestMethod.POST)
   public ResponseEntity<CommunityDTO> apiCommunity(@RequestBody CommunityDTO communityDTO) {
      log.info("Adding new community...");
      return ResponseEntity.ok(communityService.addCommunity(communityDTO));
   }

   @ApiOperation(value = "Add City", nickname = "apiCitiesCityIdPost", authorizations = @Authorization(value = "Bearer"), tags = "City")
   @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
   @RequestMapping(value = "/api/cities", consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"}, method = RequestMethod.PUT)
   public ResponseEntity<CityDTO> apiCitiesCity(@RequestBody CityDTO cityDTO) {
      log.info("Adding new city...");
      return ResponseEntity.ok(cityService.addCity(cityDTO));

   }

   @ApiOperation(value = "Login User", nickname = "apiLoginPost", tags = "User")
   @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
   @RequestMapping(value = "/api/login", consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"}, method = RequestMethod.POST)
   public ResponseEntity<LoginResponse> apiLogin(@Valid @RequestBody LoginRequest loginRequest) {
      log.info("User login...");
      LoginResponse loginResponse = userService.login(loginRequest);
      return ResponseEntity.ok(loginResponse);

   }

   @ApiOperation(value = "Add Community to User", nickname = "apiCommunitySignupPost", authorizations = @Authorization(value = "Bearer"), tags = "Community")
   @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
   @RequestMapping(value = "/api/communitySignup", consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"}, method = RequestMethod.POST)
   public ResponseEntity<UserDTO> apiCommunitySignup(@RequestBody CommunitySignupRequest communitySignupRequest) {
      Community communityById = communityService.getCommunityById(communitySignupRequest.getCommunityId());
      UserDTO userDTO = userService.attachCommunityToUser(communitySignupRequest.getUserId(), communityById);

      return ResponseEntity.ok(userDTO);
   }

   @ApiOperation(value = "User Account Activation", nickname = "apiActivateUserAccount", tags = "User")
   @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
   @RequestMapping(value = "/api/activate", method = RequestMethod.GET)
   public ResponseEntity<UserDTO> apiActivateUserAccount(@RequestParam("activationToken") String activationToken) {
      log.debug("Activating user account...");
      UserDTO userDTO = userService.verifyTokenAndActivateUser(activationToken);;
      return ResponseEntity.ok(userDTO);
   }

   @ApiOperation(value = "Get All Cities", nickname = "apiGetCities", tags = "City")
   @ApiResponses(value = @ApiResponse(code = 200, message = "Success"))
   @RequestMapping(value = "/api/cities", method = RequestMethod.GET)
   public ResponseEntity<List<CityDTO>> apiGetCities() {
      log.debug("Activating user account...");
      List<CityDTO> cities = cityService.getAllCities();;
      return ResponseEntity.ok(cities);
   }

}

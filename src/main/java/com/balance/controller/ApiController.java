package com.balance.controller;

import com.balance.model.Community;
import com.balance.model.dto.*;
import com.balance.service.CityService;
import com.balance.service.CommunityService;
import com.balance.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApiController {

   private final UserService userService;
   private final CommunityService communityService;
   private final CityService cityService;

   @ApiOperation(value = "Sign Up User", nickname = "apiSignupPost", notes = "", authorizations = {
         @Authorization(value = "Bearer")
   }, tags = {"Users"})
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Success")})
   @RequestMapping(value = "/api/signup",
         consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"},
         method = RequestMethod.POST)
   public ResponseEntity<Void> apiSignupPost(@Valid @RequestBody SignupRequest signupRequest) {
      log.info("Adding new user...");
      userService.signup(signupRequest);
      return new ResponseEntity<>(HttpStatus.OK);
   }


   @ApiOperation(value = "Create Community", nickname = "apiCommunityPost", notes = "", authorizations = {
         @Authorization(value = "Bearer")
   }, tags = {"Community"})
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Success")})
   @RequestMapping(value = "/api/community",
         consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"},
         method = RequestMethod.POST)
   public ResponseEntity<CommunityDTO> apiCommunityPost(@RequestBody CommunityDTO communityDTO) {
      log.info("Adding new community...");
      return ResponseEntity.ok(communityService.addCommunity(communityDTO));
   }

   @ApiOperation(value = "Add City", nickname = "apiCitiesCityIdPost", authorizations = {
         @Authorization(value = "Bearer")
   }, tags = {"Cities"})
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Success")})
   @RequestMapping(value = "/api/cities/",
         consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"},
         method = RequestMethod.PUT)
   public ResponseEntity<CityDTO> apiCitiesCityPost(@RequestBody CityDTO cityDTO) {
      log.info("Adding new city...");
      return ResponseEntity.ok(cityService.addCity(cityDTO));

   }

   @ApiOperation(value = "Login User", nickname = "apiLoginPost", authorizations = {
         @Authorization(value = "Bearer")
   }, tags = {"Users"})
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Success")})
   @RequestMapping(value = "/api/login",
         consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"},
         method = RequestMethod.POST)
   public ResponseEntity<LoginResponse> apiLoginPost(@Valid @RequestBody LoginRequest loginRequest) {
      log.info("User login...");
      LoginResponse loginResponse = userService.login(loginRequest);
      return ResponseEntity.ok(loginResponse);

   }

   @ApiOperation(value = "Add Community to User", nickname = "apiCommunitysignupPost", authorizations = {
         @Authorization(value = "Bearer")
   }, tags = {"Community"})
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Success")})
   @RequestMapping(value = "/api/communitysignup",
         consumes = {"application/json-patch+json", "application/json", "text/json", "application/_*+json"},
         method = RequestMethod.POST)
   public ResponseEntity<UserDTO> apiCommunitysignupPost(@RequestBody CommunitySignupRequest communitySignupRequest) {
      Community communityById = communityService.getCommunityById(communitySignupRequest.getCommunityId());
      UserDTO userDTO = userService.signupCommunityToUser(communitySignupRequest.getUserId(), communityById);

      return ResponseEntity.ok(userDTO);

   }

}

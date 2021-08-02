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
	
	@ApiModelProperty(example = "HXbk5Zk6")
	@JsonProperty("password")
	private String password;

	@ApiModelProperty(example = "john.doe@email.com")
	@JsonProperty("username")
	private String username;

	@ApiModelProperty(example = "New York")
	@JsonProperty("city")
	private String city;

	@ApiModelProperty(example = "1818 Broadway Street, New York")
	@JsonProperty("address")
	private String address;

	@ApiModelProperty(example = "555-6668-8589")
	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@ApiModelProperty(example = "true")
	@JsonProperty("active")
	private boolean active;

	@JsonProperty("communities")
	private List<CommunityDTO> communities;

	public UserDTO(String firstName, String lastName, String username, String password, String city, String address, String phoneNumber,
			boolean active, String roles, List<CommunityDTO> communities) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.city = city;
		this.address = address;
		this.phoneNumber = phoneNumber;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

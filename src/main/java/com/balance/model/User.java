package com.balance.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column(unique = true, nullable = false)
   private String username;
   @Column(nullable = false)
   private String password;
   @Column(nullable = false)
   private String firstName;
   @Column(nullable = false)
   private String lastName;
   @Column(nullable = false)
   private String roles;
   @OneToMany(mappedBy = "user")
   private List<Community> communities;

   public User(long id, String username, String password, String firstName, String lastName, String roles, List<Community> communities) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.roles = roles;
      this.communities = communities;
   }

   public User() {
   }


   public List<Community> addCommunity(Community community) {
      if (Objects.isNull(communities)) {
         communities = new ArrayList<>();
      }
      community.setUser(this);
      communities.add(community);
      return communities;
   }


   public long getId() {
      return this.id;
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.password;
   }

   public String getFirstName() {
      return this.firstName;
   }

   public String getLastName() {
      return this.lastName;
   }

   public String getRoles() {
      return this.roles;
   }

   public List<Community> getCommunities() {
      return this.communities;
   }

   public void setId(long id) {
      this.id = id;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setRoles(String roles) {
      this.roles = roles;
   }

   public void setCommunities(List<Community> communities) {
      this.communities = communities;
   }
}

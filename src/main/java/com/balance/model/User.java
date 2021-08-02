package com.balance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column(unique = true, nullable = false)
   private String email;
   @Column(nullable = false)
   private String password;
   @Column(nullable = false)
   private String firstName;
   @Column(nullable = false)
   private String lastName;
   @Column(nullable = false)
   private String roles;
   @Column(nullable = false)
   private boolean active;
   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private List<Community> communities;

   public void addCommunity(Community community) {
      if (Objects.isNull(communities)) {
         communities = new ArrayList<>();
      }
      if (communities.contains(community)) {
         community.setUser(this);
         communities.add(community);
      }
   }

   public long getId() {
      return this.id;
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

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }
}

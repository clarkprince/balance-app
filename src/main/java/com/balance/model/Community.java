package com.balance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "community")
public class Community implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column(unique = true, nullable = false)
   private String name;
   @ManyToOne
   private City city;
   @ManyToOne
   private User user;


   public Community(long id, String name, City city, User user) {
      this.id = id;
      this.name = name;
      this.city = city;
      this.user = user;
   }

   public Community() {
   }

   public long getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public City getCity() {
      return this.city;
   }

   public User getUser() {
      return this.user;
   }

   public void setId(long id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setCity(City city) {
      this.city = city;
   }

   public void setUser(User user) {
      this.user = user;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Community community = (Community) o;
      return id == community.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }
}

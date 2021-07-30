package com.balance.model;

import javax.persistence.*;

@Entity
@Table(name = "community")
public class Community {

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
}

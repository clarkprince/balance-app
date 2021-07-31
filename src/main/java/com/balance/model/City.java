package com.balance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "city")
public class City implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column(unique = true, nullable = false)
   private String name;

   @OneToMany(mappedBy = "city")
   private List<Community> communities;

   public City(long id, String name, List<Community> communities) {
      this.id = id;
      this.name = name;
      this.communities = communities;
   }

   public City() {
   }

   public long getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public List<Community> getCommunities() {
      return this.communities;
   }

   public void setId(long id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setCommunities(List<Community> communities) {
      this.communities = communities;
   }
}

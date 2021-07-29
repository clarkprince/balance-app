package com.balance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
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



}

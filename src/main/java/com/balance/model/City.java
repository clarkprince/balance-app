package com.balance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column(unique = true, nullable = false)
   private String name;

   @OneToMany(mappedBy = "city")
   private List<Community> communities;
}

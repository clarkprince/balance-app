package com.balance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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


   public List<Community> addCommunity(Community community) {
      if (Objects.isNull(communities)) {
         communities = new ArrayList<>();
      }
      community.setUser(this);
      communities.add(community);
      return communities;
   }


}

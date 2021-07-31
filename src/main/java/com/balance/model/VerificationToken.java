package com.balance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verification_token")
public class VerificationToken implements Serializable {

   @GeneratedValue(strategy = GenerationType.AUTO)
   @Id
   private long id;

   @Column(nullable = false)
   private String token;

   @JoinColumn(name = "username", referencedColumnName = "username")
   @OneToOne(cascade = CascadeType.ALL)
   private User user;

   @Column(nullable = false)
   private Date startDate;

   @Column(nullable = false)
   private Date endDate;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Date getEndDate() {
      return endDate;
   }

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public void setExpiration(Date startDate) {
      this.startDate = startDate;
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.DATE, 1);
      this.endDate = calendar.getTime();
   }
}

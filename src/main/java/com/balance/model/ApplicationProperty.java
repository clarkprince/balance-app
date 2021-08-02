package com.balance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "application_property")
public class ApplicationProperty implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column(unique = true, nullable = false)
   private String name;
   @Column
   private String value;
   @Column
   private long version;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public long getVersion() {
      return version;
   }

   public void setVersion(long version) {
      this.version = version;
   }

   public void incrementVersion() {
      this.version++;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ApplicationProperty that = (ApplicationProperty) o;
      return id == that.id &&
            version == that.version;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, version);
   }
}

package com.balance.repository;

import com.balance.model.ApplicationProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationPropertyRepository extends JpaRepository<ApplicationProperty, Long> {

   Optional<ApplicationProperty> findByName(String name);

}

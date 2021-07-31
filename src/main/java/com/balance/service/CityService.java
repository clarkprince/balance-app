package com.balance.service;

import com.balance.model.City;
import com.balance.model.dto.CityDTO;
import com.balance.repository.CityRepository;
import org.springframework.stereotype.Component;

@Component
public class CityService {
   private final CityRepository cityRepository;

   public CityService(CityRepository cityRepository) {
      this.cityRepository = cityRepository;
   }


   public CityDTO addCity(CityDTO cityDTO){
      City city = new City();
      city.setName(cityDTO.getName());
      City save = cityRepository.save(city);
      cityDTO.setCityId(save.getId());
      return cityDTO;
   }
}

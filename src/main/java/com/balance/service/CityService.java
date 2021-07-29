package com.balance.service;

import com.balance.repository.CityRepository;
import com.balance.model.City;
import com.balance.model.dto.CityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CityService {
   private final CityRepository cityRepository;


   public CityDTO addCity(CityDTO cityDTO){
      City city = new City();
      city.setName(cityDTO.getName());
      City save = cityRepository.save(city);
      cityDTO.setCityId(save.getId());
      return cityDTO;
   }
}

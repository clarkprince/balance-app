package com.balance.service;

import com.balance.model.City;
import com.balance.model.dto.CityDTO;
import com.balance.repository.CityRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityService {

   private final CityRepository cityRepository;

   public CityService(CityRepository cityRepository) {
      this.cityRepository = cityRepository;
   }

   public CityDTO addCity(CityDTO cityDTO) {
      City city = createCity(cityDTO.getName());
      City save = cityRepository.save(city);
      cityDTO.setCityId(save.getId());
      return cityDTO;
   }

   public List<CityDTO> getAllCities() {
      return cityRepository.findAll()
            .stream()
            .map(city -> new CityDTO(city.getId(), city.getName()))
            .collect(Collectors.toList());
   }

   private City createCity(String name) {
      City city = new City();
      city.setName(name);
      return city;
   }
}

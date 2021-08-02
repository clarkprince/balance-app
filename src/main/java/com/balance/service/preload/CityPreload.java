package com.balance.service.preload;

import com.balance.model.City;
import com.balance.repository.ApplicationPropertyRepository;
import com.balance.repository.CityRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CityPreload extends PreloadBase<City> {

   private static final String CITY_RESOURCE = "city";
   private final CityRepository cityRepository;

   protected CityPreload(ApplicationPropertyRepository applicationPropertyRepository, CityRepository cityRepository) {
      super(applicationPropertyRepository);
      this.cityRepository = cityRepository;
   }

   @Override
   public String getResourceName() {
      return CITY_RESOURCE;
   }

   @Override
   public void preload(Collection<City> data) {
      cityRepository.saveAll(data);
   }

   @Override
   public Collection<City> dataMap(Collection<String> lines) {
      return lines.stream().map(line -> {
         City city = new City();
         city.setName(line.trim());
         return city;
      }).collect(Collectors.toList());
   }
}

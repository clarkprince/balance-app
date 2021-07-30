package com.balance.service;


import com.balance.model.City;
import com.balance.model.Community;
import com.balance.model.dto.CommunityDTO;
import com.balance.repository.CityRepository;
import com.balance.repository.CommunityRepository;
import org.springframework.stereotype.Component;

@Component
public class CommunityService {

   private final CommunityRepository communityRepository;
   private final CityRepository cityRepository;

   public CommunityService(CommunityRepository communityRepository, CityRepository cityRepository) {
      this.communityRepository = communityRepository;
      this.cityRepository = cityRepository;
   }

   public CommunityDTO addCommunity(CommunityDTO communityDTO) {
      City city = cityRepository.getById(communityDTO.getCityId());
      Community community = new Community();
      community.setCity(city);
      community.setName(communityDTO.getName());
      Community save = communityRepository.save(community);
      communityDTO.setCommunityId(save.getId());
      return communityDTO;
   }

   public Community getCommunityById(long id) {
      return communityRepository.getById(id);
   }


}

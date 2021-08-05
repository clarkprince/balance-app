package com.balance.service;

import com.balance.IntegrationTest;
import com.balance.model.Community;
import com.balance.model.dto.CommunityDTO;
import com.balance.repository.CityRepository;
import com.balance.repository.CommunityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static com.balance.TestUtils.prepareCommunityDtoWithoutId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommunityServiceTest extends IntegrationTest {
   @Autowired
   private CommunityRepository communityRepository;
   @Autowired
   private CityRepository citiRepository;

   private CommunityService communityService;

   @BeforeAll
   public void init(){
      communityService = new CommunityService(communityRepository, citiRepository);
   }

   @Test
   @Sql({"classpath:/sql/cities.sql", "classpath:/sql/communities.sql"})
   public void shouldAddCommunity() {
      //given
      CommunityDTO communityDTO = prepareCommunityDtoWithoutId(213129L);

      //when
      CommunityDTO actualCommunity = communityService.addCommunity(communityDTO);

      //then
      Optional<Community> communityFromDb = communityRepository.findByName(communityDTO.getName());
      assertTrue(communityFromDb.isPresent());

      assertEquals(communityDTO.getName(), actualCommunity.getName());
      assertEquals(communityFromDb.get().getName(), actualCommunity.getName());
   }

   @Test
   @Sql({"classpath:/sql/cities.sql", "classpath:/sql/communities.sql"})
   public void shouldGetCommunityById() {
      //given
      long existsCommunityId = 100300L;
      //when
      Community actualCommunity = communityService.getCommunityById(existsCommunityId);

      //then
      Community communityFromDb = communityRepository.getById(existsCommunityId);
      assertEquals(actualCommunity.getId(), existsCommunityId);
      assertEquals(communityFromDb.getName(), actualCommunity.getName());
   }

}
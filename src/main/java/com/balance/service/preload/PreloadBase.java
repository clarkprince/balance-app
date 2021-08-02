package com.balance.service.preload;

import com.balance.model.ApplicationProperty;
import com.balance.repository.ApplicationPropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public abstract class PreloadBase<T> {
   private static final Logger log = LoggerFactory.getLogger(PreloadBase.class);

   private static final String PRELOAD_DATE_PATTERN = "/preload/%s-%d";
   private static final String PRELOAD_PREFIX = "preload_";
   private final ApplicationPropertyRepository applicationPropertyRepository;

   protected PreloadBase(ApplicationPropertyRepository applicationPropertyRepository) {
      this.applicationPropertyRepository = applicationPropertyRepository;
   }

   public abstract String getResourceName();

   public abstract void preload(Collection<T> data);

   public abstract Collection<T> dataMap(Collection<String> lines);

   @PostConstruct // todo: put in transaction
   public void preload() {
      String preloadPath = getPreloadPath();
      URL url = PreloadBase.class.getResource(preloadPath);
      if (Objects.nonNull(url)) {
         preload(preloadPath, url);
         incrementPropertyVersion();
      } else {
         log.info("File {} does not exist, no updates needed for {} preload", preloadPath, getResourceName());
      }
   }

   private void preload(String preloadPath, URL url) {
      BufferedReader reader;
      try (InputStream resource = (InputStream) url.getContent()) {
         reader = new BufferedReader(new InputStreamReader(resource));
         log.info("Preloading data from {}", preloadPath);
         List<String> lines = reader.lines().collect(toList());
         Collection<T> data = dataMap(lines);
         preload(data);
         log.info("Data {} loaded successfully", preloadPath);
      } catch (IOException e) {
         log.error("Failed to preload cities from file");
      }
   }

   public void incrementPropertyVersion() {
      ApplicationProperty applicationProperty = findOrCreateApplicationProperty();
      applicationProperty.incrementVersion();
      applicationPropertyRepository.save(applicationProperty);
      log.info("Property {} version incremented successfully, updated version: {}", applicationProperty.getName(), applicationProperty.getVersion());
   }

   private ApplicationProperty findOrCreateApplicationProperty() {
      String preloadProperty = getPreloadProperty();
      return applicationPropertyRepository.findByName(preloadProperty).orElseGet(() -> {
         ApplicationProperty applicationProperty = new ApplicationProperty();
         applicationProperty.setName(preloadProperty);
         applicationProperty.setVersion(0L);
         return applicationProperty;
      });
   }

   private String getPreloadPath() {
      String resourceName = getResourceName();
      String propertyName = getPreloadProperty();
      Long version = applicationPropertyRepository.findByName(propertyName).map(ApplicationProperty::getVersion)
            .orElse(0L);
      return format(PRELOAD_DATE_PATTERN, resourceName, version + 1);
   }

   private String getPreloadProperty() {
      return PRELOAD_PREFIX + getResourceName();
   }


}

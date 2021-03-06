package com.target.myRetail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

public class AppConfig {
  @Autowired
  private MongoDbFactory mongoDbFactory;
  @Autowired
  private MongoMappingContext mongoMappingContext;

  @Bean
  public MappingMongoConverter mappingMongoConverter() {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(this.mongoDbFactory);
    MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext);
    converter.setTypeMapper(new DefaultMongoTypeMapper((String)null));
    return converter;
  }
}

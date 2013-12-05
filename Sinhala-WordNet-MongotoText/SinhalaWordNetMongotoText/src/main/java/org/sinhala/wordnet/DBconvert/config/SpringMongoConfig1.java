package org.sinhala.wordnet.DBconvert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig1 {

	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		UserCredentials uc = new UserCredentials("wordnet", "WordNet@UOM");
		return new SimpleMongoDbFactory(new MongoClient(), "WordNet",uc);
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
				
		return mongoTemplate;
		
	}

}
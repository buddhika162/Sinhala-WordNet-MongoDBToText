package org.sinhala.wordnet.DBconvert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "WordNet";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient("192.248.15.236");
		//return new MongoClient("127.0.0.1");
	}
}
package org.sinhala.wordnet.DBconvert.core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.utilities.shakshara;

import org.sinhala.wordnet.DBconvert.config.SpringMongoConfig;
import org.sinhala.wordnet.DBcovert.model.User;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaAdjective;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaDerivationType;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaGender;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaNoun;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaOrigin;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaRoot;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaSynset;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaUsage;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaVerb;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class App {

	static shakshara sh = new shakshara();
	
	public static void main(String[] args) {

		sh.initialize();
		sh.delete();
		sh.initialize();
		
		DbHandler dbHandler = new DbHandler(sh);
		dbHandler.addSynsetToText(POS.NOUN);
		dbHandler.addSynsetToText(POS.VERB);
		dbHandler.addSynsetToText(POS.ADJECTIVE);
		dbHandler.addSynsetToText(POS.ROOT);
		dbHandler.addSynsetToText(POS.USAGE);
		dbHandler.addSynsetToText(POS.GENDER);
		dbHandler.addSynsetToText(POS.ORIGIN);
		dbHandler.addSynsetToText(POS.DERIVATIONLANG);
		
		dbHandler.addNounRelations();
		
        sh.close();
        System.out.println("done");
	}
	

}
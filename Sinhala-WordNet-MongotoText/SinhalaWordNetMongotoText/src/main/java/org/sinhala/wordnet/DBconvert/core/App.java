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
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaGender;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaNoun;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaRoot;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaSynset;
import org.sinhala.wordnet.wordnetDB.model.MongoSinhalaVerb;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class App {

	static shakshara sh = new shakshara();
	static App app = new App();
	public static void main(String[] args) {

		// For XML
		//ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");

		// For Annotation
		
		//User user = new User("mkyong", "password123");

		// save
		//mongoOperation.save(user);
		
		sh.initialize();
		sh.delete();
		//sh.initialize();
		sh.initialize();
		
		app.addNounToText();
		app.addVerbToText();
		app.addRootToText();
		app.addGenderToText();
       // sh.delete();
        //sh.close();
        //sh.initialize();
        app.addNounRelations();
       sh.close();
        System.out.println("done");
	}
	
	public void addNounToText(){
		
		
		List<MongoSinhalaSynset> hm = app.findAllLatestSynsets(POS.NOUN);
		
		 
		for (int i=0;i<hm.size();i++) {
		
			
			//System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
			MongoSinhalaNoun s=  (MongoSinhalaNoun) hm.get(i);
        	long offset = 0;
        	 //System.out.println(s);
        	 try {
     			offset =sh.addNounSynsetSakshara(s);
        		 //System.out.println(offset+"  id");
     		} catch (FileNotFoundException e) {
     			// TODO Auto-generated catch block
     			System.out.println("exep");
     			e.printStackTrace();
     		} catch (JWNLException e) {
     			// TODO Auto-generated catch block
     			System.out.println("exep");
     			e.printStackTrace();
    		}
             
         }
		
		//app.addNounRelations(hm);
	}
	
public void addNounRelations(){
	
	List<MongoSinhalaSynset> hm  = app.findAllLatestSynsets(POS.NOUN);
	HashMap<String, Integer> rootOrder = app.findRootOrder();	
	try {
		sh.addNounRelations(hm,rootOrder);
	} catch (JWNLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}
public void addVerbToText(){
		
	List<MongoSinhalaSynset> hm = app.findAllLatestSynsets(POS.VERB);
	
	 
	for (int i=0;i<hm.size();i++) {
			
			MongoSinhalaVerb s=  (MongoSinhalaVerb) hm.get(i);
        	
        	 //System.out.println(s);
        	 try {
     			sh.addVerbSynsetSakshara(s);
        		 
     		} catch (FileNotFoundException e) {
     			// TODO Auto-generated catch block
     			System.out.println("exep");
     			e.printStackTrace();
     		} catch (JWNLException e) {
     			// TODO Auto-generated catch block
     			System.out.println("exep");
     			e.printStackTrace();
    		}
             
         }
	}
public void addRootToText(){
	
	List<MongoSinhalaRoot>  hm = app.findAllLatestRoot();
	
	 
	for (int i=0;i<hm.size();i++) {
		//System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
		MongoSinhalaRoot s=  (MongoSinhalaRoot) hm.get(i);
    	long offset = 0;
    	 //System.out.println(s);
    	 try {
 			offset =sh.addRootSynsetSakshara(s);
    		 //System.out.println(offset+"  id");
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			System.out.println("exep");
 			e.printStackTrace();
 		} catch (JWNLException e) {
 			// TODO Auto-generated catch block
 			System.out.println("exep");
 			e.printStackTrace();
		}
         
     }
	
	//app.addNounRelations(hm);
}

	public void addGenderToText(){
		List<MongoSinhalaGender> hm = app.findAllLatestGender();
		
		 
		for (int i=0;i<hm.size();i++) {
			
			//System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
			MongoSinhalaGender s=  hm.get(i);
        	long offset = 0;
        	 //System.out.println(s);
        	 try {
     			offset =sh.addGenderSynsetSakshara(s);
        		 //System.out.println(offset+"  id");
     		} catch (FileNotFoundException e) {
     			// TODO Auto-generated catch block
     			System.out.println("exep");
     			e.printStackTrace();
     		} catch (JWNLException e) {
     			// TODO Auto-generated catch block
     			System.out.println("exep");
     			e.printStackTrace();
    		}
             
         }
	}
	
	public List<MongoSinhalaSynset> findAllLatestSynsets(POS pos){
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		List<MongoSinhalaSynset> collection = new ArrayList<MongoSinhalaSynset>();
		if(pos == POS.NOUN){
		List<MongoSinhalaNoun> ncollection = mongoOperation.findAll(MongoSinhalaNoun.class);
		for (MongoSinhalaNoun s : ncollection) {	
			collection.add(s);
		}
		
		}
		if(pos == POS.VERB){
			List<MongoSinhalaVerb> vcollection = mongoOperation.findAll(MongoSinhalaVerb.class);
			for (MongoSinhalaVerb s : vcollection) {	
				collection.add(s);
			}
			
			}
		
		if(pos == POS.ADJECTIVE){
			List<MongoSinhalaAdjective> acollection = mongoOperation.findAll(MongoSinhalaAdjective.class);
			for (MongoSinhalaAdjective s : acollection) {	
				collection.add(s);
			}
			
			}
		List<MongoSinhalaSynset> distinctCollection = new ArrayList<MongoSinhalaSynset>();
		List<Long> ids = new ArrayList<Long>();
		
		for (MongoSinhalaSynset s : collection) {	
			
			if(!ids.contains(s.getEWNId())){
				ids.add(s.getEWNId());
				distinctCollection.add(s);
			}
			else{
				int position = ids.indexOf(s.getEWNId());
				ids.remove(position);
				distinctCollection.remove(position);
				ids.add(s.getEWNId());
				distinctCollection.add(s);
			}
			
			
			}
		return distinctCollection;
		
	}
	
	
	public List<MongoSinhalaRoot> findAllLatestRoot(){
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		
		List<MongoSinhalaRoot> collection = mongoOperation.findAll(MongoSinhalaRoot.class);
		List<MongoSinhalaRoot> distinctCollection = new ArrayList<MongoSinhalaRoot>();
		List<String> words = new ArrayList<String>();
		HashMap<String,MongoSinhalaRoot> hm = new HashMap<String,MongoSinhalaRoot>();
		for (MongoSinhalaRoot s : collection) {	
			if(!words.contains(s.getWordsAsString())){
				words.add(s.getWordsAsString());
				distinctCollection.add(s);
			}
			else{
				int position = words.indexOf(s.getWordsAsString());
				words.remove(position);
				distinctCollection.remove(position);
				words.add(s.getWordsAsString());
				distinctCollection.add(s);
			}
			hm.put(s.getWordsAsString(), s);
			
			}
		return distinctCollection;
		
	}
	
	public List<MongoSinhalaGender> findAllLatestGender(){
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		
		
		List<MongoSinhalaGender> collection = mongoOperation.findAll(MongoSinhalaGender.class);
		List<MongoSinhalaGender> distinctCollection = new ArrayList<MongoSinhalaGender>();
		List<String> words = new ArrayList<String>();
		
		for (MongoSinhalaGender s : collection) {	
			if(!words.contains(s.getWordsAsString())){
				words.add(s.getWordsAsString());
				distinctCollection.add(s);
			}
			else{
				int position = words.indexOf(s.getWordsAsString());
				words.remove(position);
				distinctCollection.remove(position);
				words.add(s.getWordsAsString());
				distinctCollection.add(s);
			}
			
			
			}
		return distinctCollection;
		
	}
	
	public HashMap<String, Integer> findRootOrder(){
		
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
		List<MongoSinhalaRoot> collection = mongoOperation.findAll(MongoSinhalaRoot.class);
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		
		List<MongoSinhalaRoot> distinctCollection = new ArrayList<MongoSinhalaRoot>();
		List<String> words = new ArrayList<String>();
		for (MongoSinhalaRoot s : collection) {	
			if(!words.contains(s.getWordsAsString())){
				words.add(s.getWordsAsString());
				distinctCollection.add(s);
			}
			else{
				int position = words.indexOf(s.getWordsAsString());
				words.remove(position);
				distinctCollection.remove(position);
				words.add(s.getWordsAsString());
				distinctCollection.add(s);
			}
			
			
			}
		int i=0;
		for(MongoSinhalaRoot s : distinctCollection){
			hm.put(s.getId(), i);
			//System.out.println("sid"+s.getId());
			i++;
		}
		
		return hm;
		
	}

}
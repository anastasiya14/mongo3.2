package Interfaces.Impl;


import Interfaces.FileIdAll;
import Interfaces.NumbersSquareAll;
import POJOjson.nSquare;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.and;

/**
 * Created: 05.06.16 23:18
 *
 * @author Anastasiya Plotnikova
 */
public class NumberSquareAllImpl implements NumbersSquareAll{
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(NumberSquareAllImpl.class));


    public List<Long> findSquareAllId() {
        List<Long> result = new ArrayList<Long>();
        Set<Long> set = new HashSet<Long>();
        MongoClient mongoClient = null;
        try {

            //int limit = 20000;

            List<String> filter = new ArrayList<String>();
            filter.add("nSquare");


            mongoClient = new MongoClient("10.130.101.9", 27017);

            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("preWeb_test3");

            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find()
                    .projection(and(Projections.excludeId(), Projections.include(filter)))
                    .limit(400)
                    .iterator();

            try {
                while (cursor.hasNext()) {


                    String json = cursor.next().toJson();
                    LOGGER.info(json);

                    // this is the key object to convert JSON to Java
                    ObjectMapper mapper = new ObjectMapper();

                    nSquare squareId = mapper.readValue(json, nSquare.class);
                    LOGGER.info("nSquare: "+squareId.getnSquare().values().iterator().next().toString());
                    set.add(squareId.getnSquare().values().iterator().next());

                }
                mongoClient.close();
            } catch (Exception w) {
            }
        } finally {
            mongoClient.close();
        }

        result.clear();
        result.addAll(set);
        System.out.println(result.size());
        System.out.println(result);
        return result;
    }
}

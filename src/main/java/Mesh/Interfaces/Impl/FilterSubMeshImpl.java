package Mesh.Interfaces.Impl;


import Mesh.Interfaces.FilterSubMesh;
import Mesh.POJOjson.SquaresSort;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;

/**
 * Created: 06.06.16 10:11
 *
 * @author Anastasiya Plotnikova
 */
public class FilterSubMeshImpl implements FilterSubMesh {
    private static final Long timeZone = (long) 288;

    public Map<String, Long> filterSubMesh(String startCollectionName) {
        MongoClient mongoClient = null;
        List<String> nSquareJSON = new ArrayList<String>();
        List<List<String>> result = new ArrayList<List<String>>();
        Map<String, Map<Long, Long>> squareNumberFileId = new HashMap<String, Map<Long, Long>>();

        Map<Long, Long> numberFileId = new HashMap<Long, Long>();


        Map<String, Long> numberRepetition = new HashMap<String, Long>();
        Long id = null;
        try {

            List<String> filter = new ArrayList<String>();
            filter.add("nSquare");
            filter.add("timeZone");
            filter.add("weekDay");
            filter.add("fileId");
            filter.add("squareI");
            filter.add("squareJ");


            //mongoClient = new MongoClient("10.130.101.9", 27017);
            mongoClient = new MongoClient("127.0.0.1", 27017);


            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection(startCollectionName);

            //Query MongoDB
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find()
                    .projection(and(Projections.excludeId(), Projections.include(filter)))
                    .limit(10)
                    .iterator();

            int i = 1;
            try {
                while (cursor.hasNext()) {

                    System.out.println(i++);

                    String json = cursor.next().toJson();
                    //nSquareJSON.add(json);
                    ObjectMapper mapper = new ObjectMapper();

                    SquaresSort squareId = mapper.readValue(json, SquaresSort.class);

                    if (!numberRepetition.containsKey(json)) { //проверяет есть ли в мапе json

                        numberRepetition.put(json, (long) 1);

                    } else {

                        numberRepetition.put(json, numberRepetition.get(json) + 1);
                    }


                }
                mongoClient.close();
            } catch (Exception w) {
            } finally {
                cursor.close();
            }

        } finally {
            mongoClient.close();
        }
        return numberRepetition;
    }
}

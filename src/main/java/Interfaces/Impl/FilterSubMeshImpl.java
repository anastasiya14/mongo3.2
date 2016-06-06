package Interfaces.Impl;

import Interfaces.FilterSubMesh;
import POJOjson.nSquare;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;

/**
 * Created: 06.06.16 10:11
 *
 * @author Anastasiya Plotnikova
 */
public class FilterSubMeshImpl implements FilterSubMesh {
    private static final Long timeZone = (long) 288;

    public void filterSubMesh(List<Long> numberSquare) {
        MongoClient mongoClient = null;
        try {

            List<String> filter = new ArrayList<String>();
            filter.add("nSquare");
            filter.add("timeZone");
            filter.add("weekDay");
            filter.add("fileId");
            for (int i = 0; i < numberSquare.size(); i++) {
                for (int j = 1; j < timeZone; j++) {
                    System.out.println("timeZone: "+timeZone);
                    mongoClient = new MongoClient("10.130.101.9", 27017);

                    // New way to get database
                    MongoDatabase db = mongoClient.getDatabase("moto");
                    MongoCollection<Document> collection = db.getCollection("preWeb_test2");
                    //for (int i=0; i<numberSquare.size();i++) {

                    MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(
                            and(new Document("nSquare", 632292), new Document("timeZone", (long) j))

                    )
                            .projection(and(Projections.excludeId(), Projections.include(filter)))
                            .limit(3)
                            .iterator();

                    try {
                        while (cursor.hasNext()) {


                            String json = cursor.next().toJson();
                            System.out.println(json);


                            // this is the key object to convert JSON to Java
                            // ObjectMapper mapper = new ObjectMapper();

                            //nSquare squareId = mapper.readValue(json, nSquare.class);


                        }
                        mongoClient.close();
                    } catch (Exception w) {
                    }
                }
            }
        } finally {
            mongoClient.close();
        }

    }
}

package Mesh.Interfaces.Impl;


import Mesh.Interfaces.FilterSubMesh;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

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

    public List<String> filterSubMesh() {
        MongoClient mongoClient = null;
        List<String> nSquareJSON = new ArrayList<String>();
        List<List<String>> result = new ArrayList<List<String>>();

        try {

            List<String> filter = new ArrayList<String>();
            filter.add("nSquare");
            filter.add("timeZone");
            filter.add("weekDay");
            filter.add("fileId");
            filter.add("squareI");
            filter.add("squareJ");


            mongoClient = new MongoClient("10.130.101.9", 27017);

            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("preWeb_test3");

            //Query MongoDB
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find()
                    .projection(and(Projections.excludeId(), Projections.include(filter)))
                    //.limit(1000000)
                    .iterator();

            try {
                while (cursor.hasNext()) {

                    String json = cursor.next().toJson();
                    nSquareJSON.add(json);

                }
                mongoClient.close();
            } catch (Exception w) {
            }
            //result.add(nSquareJSON);

        } finally {
            mongoClient.close();
        }
        // System.out.println("Группы ТС " + result);
        return nSquareJSON;
    }
}

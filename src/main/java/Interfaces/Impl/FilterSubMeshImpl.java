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

        List<List<String>> result = new ArrayList<List<String>>();

        try {

            List<String> filter = new ArrayList<String>();
            filter.add("nSquare");
            filter.add("timeZone");
            filter.add("weekDay");
            filter.add("fileId");
            for (int i = 0; i < numberSquare.size(); i++) {

                List<String> nSquareJSON = new ArrayList<String>();

                for (int j = 1; j <= timeZone; j++) {
                    System.out.println("timeZone: " + j);

                    mongoClient = new MongoClient("10.130.101.9", 27017);

                    // New way to get database
                    MongoDatabase db = mongoClient.getDatabase("moto");
                    MongoCollection<Document> collection = db.getCollection("preWeb_test2");

                    //Query MongoDB
                    MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(
                            and(new Document("nSquare", numberSquare.get(i)), new Document("timeZone", (long) j)))
                            .projection(and(Projections.excludeId(), Projections.include(filter)))
                            .limit(1)
                            .iterator();

                    try {
                        while (cursor.hasNext()) {

                            String json = cursor.next().toJson();
                            System.out.println(json);
                            nSquareJSON.add(json);


                        }
                        mongoClient.close();
                    } catch (Exception w) {
                    }
                }
                System.out.println("LIST  "+nSquareJSON);
                result.add(nSquareJSON);

            }

        } finally {
            mongoClient.close();
        }
        System.out.println("Группы ТС "+result);
    }
}

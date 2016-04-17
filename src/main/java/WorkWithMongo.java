
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.*;

import static com.mongodb.client.model.Filters.*;


import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created: 28.03.16 17:07
 *
 * @author Anastasiya Plotnikova
 */
public class WorkWithMongo {
    private MongoCursor<Document> cursor;
private final double longitudeKm=4.49;
    private final double equator=111.3213778;
    public void MongoConnect(int fileIdstart, long gentime, double latitude,
                             double longitude,double latitudeEnd, double longitudeEnd,
                             int fileIdend) throws Exception {
        //latitude=42.411171
        //longitude=-83.874336
        double eps=equator*Math.cos(latitude*Math.PI/180);
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            // New way to get collection
            MongoCollection<Document> collection = db.getCollection("traffic");
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(
                    new Document().append("fileId", new Document("$ne", fileIdstart))
                            .append("gentime", new Document("$lte", gentime + 500000000))
                            .append("gentime", new Document("$gte", gentime - 500000000))
                            .append("longitude", new Document("$lte",longitude+longitudeKm))
                            .append("longitude", new Document("$gte",longitude-longitudeKm))
                            .append("latitude",new Document("$lte",latitude+eps))
                            .append("latitude",new Document("$gte",latitude-eps))
            ).limit(25).iterator();

            try {
                while (cursor.hasNext()) {

                    System.out.println(cursor.next());
                }
                mongoClient.close();
            } catch (Exception w) {
            }
        } finally {
            mongoClient.close();
        }


    }
}

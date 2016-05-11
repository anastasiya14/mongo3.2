
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
    //TODO исправить этот треш
    private MongoCursor<Document> cursor;
    private final double longitudeKm = 0.4499;
    private final double equator = 111321.3778;

    public void MongoConnect(int fileIdstart, long gentime, double latitude,
                             double longitude, double latitudeEnd, double longitudeEnd,
                             int fileIdend) throws Exception {

        double eps = (355 * 100) / (equator * Math.cos(latitude * Math.PI / 180));
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient("10.130.101.9", 27017);
            // mongoClient = new MongoClient("127.0.0.1",27017);

            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("traffic");
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(and(
                    lte("gentime", gentime + 500000000),
                    gte("gentime", gentime - 500000000),
                    lte("longitude", longitude + longitudeKm),//меньше
                    gte("longitude", longitude - longitudeKm), //больше
                    lte("latitude", latitude + eps),
                    gte("latitude", latitude - eps)
                    )
            ).limit(50000).iterator();

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

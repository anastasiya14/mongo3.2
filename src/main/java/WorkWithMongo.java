
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.*;

import static com.mongodb.client.model.Filters.*;


import java.net.UnknownHostException;
import java.util.*;


/**
 * Created: 28.03.16 17:07
 *
 * @author Anastasiya Plotnikova
 */
public class WorkWithMongo {
    //TODO исправить этот треш
    /**
     */

    private MongoCursor<Document> cursor;
    //private final double longitudeKm = 0.4499;
    // private final double equator = 111321.3778;

    public void MongoConnect(Map<List<String>, List<String>> mapFileId) throws Exception {

        MongoClient mongoClient = null;
        try {
            List<String> fileid = new ArrayList<String>();
            List<String> location = new ArrayList<String>();

            mongoClient = new MongoClient("10.130.101.9", 27017);
            BasicDBObject filter = new BasicDBObject("$nearSphere", new double[]{-73.99171, 40.738868});
            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("traffic");
            mapFileId.values();


            for (List<String> keyList : mapFileId.keySet()) {
                fileid = keyList;
            }

            for (List<String> keyList : mapFileId.values()) {
                location = keyList;
            }

            //   for (String str : fileid) {
                 /*тут будет браться id и координаты и будет делаться запрос*/
            //   }

            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(//and(
                    //  lte("gentime", gentime + 500000000),
                    //  gte("gentime", gentime - 500000000)
                    //  lte("longitude", longitude + longitudeKm),//меньше
                    //  gte("longitude", longitude - longitudeKm), //больше
                    //  lte("latitude", latitude + eps),
                    //  gte("latitude", latitude - eps)
                    // near("loc",) //)
            ).iterator();

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

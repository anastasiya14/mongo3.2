
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

    public void MongoConnect(int rxDevice, long gentime) throws Exception {
        MongoClient mongoClient = null;
        Map<String, Object> fields = new HashMap();
        //  BasicDBObject regexQuery = new BasicDBObject();
        fields.put("rxDevice", 1566);
        // fields.put("gentime",Long.parseLong("291864243384363"));
        try {
            mongoClient = new MongoClient();
            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            // New way to get collection
            MongoCollection<Document> collection = db.getCollection("traffic");
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(
                    new Document().append("rxDevice", new Document("$ne", rxDevice))
                            .append("gentime", new Document("$lte", gentime + 500000000))
                            .append("gentime", new Document("$gte", gentime - 500000000))
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

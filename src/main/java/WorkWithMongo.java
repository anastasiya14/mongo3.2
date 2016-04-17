
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

    public void MongoConnect() throws Exception {
        MongoClient mongoClient = null;
        Map<String, Object> fields = new HashMap();
        //  BasicDBObject regexQuery = new BasicDBObject();
        fields.put("rxDevice", 1566);
        // fields.put("gentime",Long.parseLong("291864243384363"));
        try {
            mongoClient = new MongoClient();
            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            // DBCollection dbCollection();
            // New way to get collection
            MongoCollection<Document> collection = db.getCollection("traffic");
            // System.out.println("collection: " + collection.toString());

            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(
                    // new BasicDBObject().append("gentime",Long.parseLong("291864243384363"))
                      new Document().append("rxDevice", new Document("$lte",1567)  )
                              .append("fileId", 1158354).append("txRandom",-9018)
                    //.append("_id","56fc020a8dd9ce97b37b5ebb")
                 .append("gentime", new Document("$type","long"))
            ).iterator();


            //System.out.println(cursor.toString());
            int i = 0;
            try {
                while (cursor.hasNext()) {
                    i++;
                    System.out.println(cursor.next());
                    if (i > 15


                            ) {
                        break;
                    }
                }
                mongoClient.close();
            } catch (Exception w) {
            }
        } finally {
            mongoClient.close();
        }


    }
}

import Mesh.Interfaces.FilterSubMesh;
import Mesh.Interfaces.Impl.FilterSubMeshImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.util.logging.Logger;

/**
 * Created by anastasiya.plotnikova on 21.05.17.
 */
public class Parallel implements Runnable {
    private static Logger logger = Logger.getLogger(String.valueOf(Parallel.class));
    private String startCollectionName;
    private String endCollectionName;

    Parallel(String startCollectionName, String endCollectionName) {
        this.startCollectionName = startCollectionName;
        this.endCollectionName = endCollectionName;
    }


    public void run() {
        final FilterSubMesh filterSubMesh = new FilterSubMeshImpl();
        MongoClient mongoClient = null;
        BasicDBList fileIdList = new BasicDBList();

        try {
            mongoClient = new MongoClient("10.130.101.9", 27017);

            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("config");

            //получаем все идентификоторы устройств из конфига
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find()
                    .iterator();
            while (cursor.hasNext()) {
                BasicDBObject fileIdConfig = (BasicDBObject) JSON.parse(cursor.next().toJson());

                fileIdList = (BasicDBList) fileIdConfig.get("fileId");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        for (String fileIdKey : fileIdList.keySet()) {
            for (double timeZone = 1; timeZone <= 288; timeZone++) {
                filterSubMesh
                        .filterSubMesh(startCollectionName, endCollectionName, timeZone, (Double) fileIdList.get(fileIdKey));
            }
        }

    }
}

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.codehaus.jackson.JsonFactory;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.and;

/**
 * Created: 22.05.16 12:14
 *
 * @author Anastasiya Plotnikova
 */
public class FileIdAllImpl implements FileIdAll {

    public List<String> FindFileId() throws UnknownHostException {
        MongoClient mongoClient = null;
        List<String> listFileId =new ArrayList<String>();

        try {
            JsonFactory jfactory = new JsonFactory();
            mongoClient = new MongoClient("10.130.101.9", 27017);

            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("test2");
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find()
                    .projection(and(Projections.include("fileId"), Projections.excludeId()))
                    //.limit(2000)
                    .iterator();
            try {
                String resultId = null;
                while (cursor.hasNext()) {
                    resultId = cursor.next().toString().replaceAll("^D[A-Za-z\\{=]+", "").replaceAll("\\}}", "");
                   // System.out.println(resultId);
                    listFileId.add(resultId);
                }
                mongoClient.close();
            } catch (Exception w) {
            }
        } finally {
            mongoClient.close();
        }


        return listFileId;
    }



    public Set<String> filterList(List<String> listFileId) {

        Set<String> set = new HashSet<String>(listFileId);
        return  set;
    }

}

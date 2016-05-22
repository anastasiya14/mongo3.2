import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.codehaus.jackson.JsonFactory;

import java.net.UnknownHostException;
import java.util.*;

import static com.mongodb.client.model.Filters.and;

/**
 * Created: 22.05.16 12:14
 *
 * @author Anastasiya Plotnikova
 */
public class FileIdAllImpl implements FileIdAll {
    //мапа содержит 1 ключ и однозначение
    public Map<List<String>, List<String>> FindFileId() throws UnknownHostException {
        MongoClient mongoClient = null;
        List<String> fileid = new ArrayList<String>();
        List<String> location = new ArrayList<String>();
        Map<List<String>, List<String>> result = new HashMap<List<String>, List<String>>();
        try {
            JsonFactory jfactory = new JsonFactory();
            mongoClient = new MongoClient("10.130.101.9", 27017);

            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("test2");
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find()
                    .projection(and(Projections.include("fileId"), Projections.include("loc"), Projections.excludeId()))
                    .limit(2000)
                    .iterator();
            try {
                String resultId = null;
                String loc = null;
                while (cursor.hasNext()) {
                    resultId = cursor.next().toString().replaceAll("[A-Za-z\\{=]+", "").replaceAll("\\}}", "").replaceAll(",", "|");
                    fileid.add(resultId.substring(0, resultId.indexOf("|")));
                    location.add(resultId.substring(resultId.indexOf("|") + 1, resultId.length()).replace(" ", ""));
                }
                mongoClient.close();
            } catch (Exception w) {
            }
        } finally {
            mongoClient.close();
        }
        //System.out.println(fileid.size());
        //System.out.println(location.size());
        result.put(fileid, location);
        System.out.println(result.size());

        return result;
    }


}

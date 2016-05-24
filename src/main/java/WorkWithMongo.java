
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import jdk.nashorn.internal.parser.JSONParser;
import org.bson.*;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


import static com.mongodb.client.model.Filters.*;


import java.io.IOException;
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

    public void MongoConnect(List<String> fileId) throws Exception {
        //срез по времени пока для одного id, по столбцу минут с начала дня.
        MongoClient mongoClient = null;
        try {
            Long gentime = 292075345520544L;
            List<String> fileid = new ArrayList<String>();
            List<String> result = new ArrayList<String>();

            int limit = 20000;

            List<String> filter = new ArrayList<String>();
            filter.add("loc");
            filter.add("fileId");
            filter.add("dayTime");
            filter.add("weekDay");

            int dayTime = 900; //в минутах - 15 часов

            mongoClient = new MongoClient("10.130.101.9", 27017);

            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");
            MongoCollection<Document> collection = db.getCollection("test2");

            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find(and(

                    //  lte("dayTime", dayTime + 5),//меньше
                    //   gte("dayTime", dayTime - 5),//больше
                    //    near("loc", -83.87365, 42.404968, (double) 500, (double) 0)


                    near("loc", -83.87365, 42.404968, (double) 500, (double) 0),
                    lte("dayTime", dayTime + 5),//меньше
                    gte("dayTime", dayTime - 5)//больше
            ))
                    .projection(and(Projections.excludeId(), Projections.include(filter)))
                    .limit(limit)
                    .iterator();


            try {
                while (cursor.hasNext()) {
                    String json = cursor.next().toJson();
                    System.out.println(json);

                    // this is the key object to convert JSON to Java
                    ObjectMapper mapper = new ObjectMapper();
                    MongoData mongoData = mapper.readValue(json, MongoData.class);
                    System.out.println("Java object created from JSON String :");
                    System.out.println(mongoData);
                    int a = dayTime - 5;
                    int b = dayTime;
                    int c = dayTime + 3;
                    int d = c + 2;
                    double phiTime = 0;
                    double x = mongoData.getDayTime();
                    if (a <= x & x < b) {
                        phiTime = (x - a) / (b - a);
                    } else if (c <= x & x <= d) {
                        phiTime = (d - x) / (d - c);
                    } else if (b <= x & b < d) {
                        phiTime = 1;
                    } else {
                        phiTime = 0;
                    }
                    System.out.println(phiTime);

                    if (phiTime >= 0.8) {

                        result.add(String.valueOf("id= " + mongoData.getFileId() + "   phi=" + phiTime));
                    }

                }
                System.out.println(result);

                System.out.println("Получено точек после фильтрации: " + result.size());
                System.out.println("Отфильтрованно точек: " + (limit - result.size()));
                mongoClient.close();
            } catch (Exception w) {
            }
        } finally {
            mongoClient.close();
        }


    }
}

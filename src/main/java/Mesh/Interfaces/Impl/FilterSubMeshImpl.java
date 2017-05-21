package Mesh.Interfaces.Impl;


import Mesh.Interfaces.FilterSubMesh;
import Mesh.POJOjson.SquaresSort;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.util.JSON;
import org.bson.BSON;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.*;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.and;

/**
 * Created: 06.06.16 10:11
 *
 * @author Anastasiya Plotnikova
 */
public class FilterSubMeshImpl implements FilterSubMesh {
    private static Logger logger = Logger.getLogger(String.valueOf(FilterSubMeshImpl.class));

    private static final double a = 0;

    private static final double b = 2000;

    public Map<String, Long> filterSubMesh(String startCollectionName, String endCollectionName, Double timeZone, Double fileId) {
        MongoClient mongoClient = null;

        Map<String, Long> numberRepetition = new HashMap<String, Long>();
        try {

            mongoClient = new MongoClient("10.130.101.9", 27017);
            // New way to get database
            MongoDatabase db = mongoClient.getDatabase("moto");

            MongoCollection<Document> collection = db.getCollection(startCollectionName);

            Document test1 = new Document();
            test1.put("$match", new Document("fileId", fileId));

            Document test2 = new Document();
            test2.put("$match", new Document("timeZone", timeZone));

            Document test = new Document();
            Document square = new Document("squareJ", "$squareJ");
            square.put("squareI", "$squareI");
            square.put("fileId", "$fileId");
            test.put("$group", new Document("_id", square));


            //получаем все квадраты в которых есть определенное устройство в определенный промежуток времени
            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.aggregate(
                    Arrays.asList(test1, test2, test))
                    .iterator();
            //System.out.println(Arrays.asList(test1.toJson(),test2.toJson(), test.toJson()));

            try {
                while (cursor.hasNext()) {
                    //i++;

                    String json = cursor.next().toJson();
                    //System.out.println(cursor.next().getClass());
                    System.out.println(json);

                    BasicDBObject jsonParse = (BasicDBObject) JSON.parse(json);
                    Document requestCount = new Document();
                    requestCount.put("squareI", ((BasicDBObject) jsonParse.get("_id")).get("squareI"));
                    requestCount.put("squareJ", ((BasicDBObject) jsonParse.get("_id")).get("squareJ"));
                    requestCount.put("fileId", ((BasicDBObject) jsonParse.get("_id")).get("fileId"));
                    requestCount.put("timeZone", timeZone);
                    Long countFileId = collection.count(requestCount);
                    System.out.println(countFileId);

                    //проверяем пренадлежит ли срезу объект
                    double alpha = 0;


                    if (a <= countFileId && countFileId <= b) {
                        alpha = ((double) ((double) countFileId - (double) a) / (double) ((double) b - (double) a));

                    } else {
                        alpha = 1;

                    }

                    if (alpha >= 0.8) {
                        System.out.println("пренадлежит срезу: " + json);
                        MongoCollection<Document> collectionMesh = db.getCollection(endCollectionName);

                        Document request = new Document();
                        request.put("squareI", ((BasicDBObject) jsonParse.get("_id")).getLong("squareI"));
                        request.put("squareJ", ((BasicDBObject) jsonParse.get("_id")).getLong("squareJ"));
                        request.put("fileId", ((BasicDBObject) jsonParse.get("_id")).get("fileId"));

                        request.put("timeZone", timeZone);
                        System.out.println(and(request).toString());
                        MongoCursor<Document> cursorMesh = collectionMesh.find(and(
                                new Document("squareI", ((BasicDBObject) jsonParse.get("_id")).getLong("squareI")),
                                new Document("squareJ", ((BasicDBObject) jsonParse.get("_id")).getLong("squareJ")),
                                //new Document("fileId",((BasicDBObject)jsonParse.get("_id")).get("fileId")),
                                new Document("timeZone", timeZone))).iterator();
                        boolean hashNext = false;
                        //System.out.println(cursorMesh!=null);
                        while (cursorMesh.hasNext()) {
                            System.out.println("Такая запись уже есть");
                            //обновление записи
                            hashNext = true;
                            Document meshJson = cursorMesh.next();
                            System.out.println(meshJson.toJson());
                            BasicDBObject meshObject = (BasicDBObject) JSON.parse(meshJson.toJson());

                            HashMap<String, Object> devicesDocument = new HashMap<String, Object>();
                            devicesDocument.put("fileId", ((BasicDBObject) jsonParse.get("_id")).get("fileId"));
                            devicesDocument.put("devProd", alpha);
                            List<HashMap<String, Object>> testwww = new ArrayList<HashMap<String, Object>>();
                            testwww.add(devicesDocument);
                            BasicDBList devicesList = ((BasicDBList) meshObject.get("devices"));

                            devicesList.add(devicesDocument);
                            meshObject.put("devices", devicesList);
                            meshObject.remove("_id");
                            System.out.println(devicesList);
                            collectionMesh.updateMany(new BasicDBObject("_id", meshJson.get("_id")), new Document("$set", meshObject));
                        }
                        if (hashNext == false) {
                            //добавляем новую запись о сетке
                            Document insertMesh = new Document();
                            insertMesh.put("squareI", ((BasicDBObject) jsonParse.get("_id")).get("squareI"));

                            insertMesh.put("squareJ", ((BasicDBObject) jsonParse.get("_id")).get("squareJ"));
                            insertMesh.put("timeZone", timeZone);
                            Document devicesDocument = new Document();
                            devicesDocument.put("fileId", ((BasicDBObject) jsonParse.get("_id")).get("fileId"));
                            devicesDocument.put("devProd", alpha);
                            insertMesh.put("devices", Arrays.asList(devicesDocument));

                            collectionMesh.insertOne(insertMesh);
                        }
                        //добавляем запись о сетке
                        //проверить есть ли такая клетка уже в базе, если есть то обновляем запись (добавляем устройство в список девайсов)

                    }

                }
                mongoClient.close();
            } catch (Exception w) {
                logger.info(w.getMessage());
            } finally {
                cursor.close();

            }

        } finally {
            mongoClient.close();
        }
        return numberRepetition;
    }
}

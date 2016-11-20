package Mesh.Interfaces.Impl;

import Mesh.Interfaces.SquareSort;
import Mesh.POJOjson.SquaresSort;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created: 07.06.16 17:51
 *
 * @author Anastasiya Plotnikova
 */
public class SquareSortImpl implements SquareSort {

    private static final double a = 1;

    private static final double b = 2400;
    //   private static final int b = 18000; //9000 записей за месяц (30 сек)
    private static final double degree = 0.8;

    public Map<String, Map<Long, Long>> numberFileIdinSquare(Map<String, Long> numberRepetition) throws IOException {

        Map<String, Map<Long, Long>> squareNumberFileId = new HashMap<String, Map<Long, Long>>();

        Map<Long, Long> numberFileId = new HashMap<Long, Long>();
        //System.out.println("numberRepetition " + numberRepetition);
        Long id = null;
        int i = 0;
        List<Long> valueFileIdAndNumber = new ArrayList<Long>();
        valueFileIdAndNumber.addAll(numberRepetition.values());

        JSONObject obj = new JSONObject();
        for (String entry : numberRepetition.keySet()) {
            //System.out.println("entry-------------------- " + entry);
            //System.out.println("++++++++++++++++++++++++ " + numberRepetition.keySet());
            ObjectMapper mapper = new ObjectMapper();
            Map<Long, Long> fileIdNum = new HashMap<Long, Long>();
            // SquaresSort squareId = mapper.readValue(entry, SquaresSort.class);


            BasicDBObject squareId = (BasicDBObject) JSON.parse(entry);
            id = Long.parseLong(squareId.get("fileId").toString());
            obj.put("squareI", ((BasicDBObject) squareId.get("square")).get("i"));
            // System.out.println("-------------------- " + ((BasicDBObject) squareId.get("square")).get("i"));
            obj.put("squareJ", ((BasicDBObject) squareId.get("square")).get("j"));
            //  System.out.println("-------------------- " + squareId.getSquareJ());
            obj.put("timeZone", squareId.get("timeZone"));
            //   System.out.println("-------------------- " + squareId.getTimeZone());
            obj.put("weekDay", squareId.get("weekDay"));
            //   System.out.println("-------------------- " + squareId.getWeekDay());

            fileIdNum.put(id, valueFileIdAndNumber.get(i));
            //  System.out.println(fileIdNum);
            if (squareNumberFileId.containsKey(obj.toString())) {
                // System.out.println("TRUE");
                Map<Long, Long> temp = new HashMap<Long, Long>();
                temp = squareNumberFileId.get(obj.toJSONString());
                temp.put(id, valueFileIdAndNumber.get(i));
                squareNumberFileId.put(obj.toJSONString(), temp);
            } else {
                squareNumberFileId.put(obj.toJSONString(), fileIdNum);
            }
            i++;
            // obj = null;
            System.gc();
            //  System.out.println("-------------------- " + squareNumberFileId);
        }
        //System.out.println("squareNumberFileId " + squareNumberFileId);
        return squareNumberFileId;
    }


    public Map<String, Map<Long, Double>> assessment(Map<String, Map<Long, Long>> nSquare) throws IOException {
        // System.out.println("++++++++++++++++++++++++++++++++++++ ");

        //  System.out.println(nSquare);
        // System.out.println("++++++++++++++++++++++++++++++++++++ ");
        /* оценка записей, удаление лишних*/
        //System.out.print(nSquare);
        Map<String, Map<Long, Double>> result = new HashMap<String, Map<Long, Double>>();

        List<String> valueString = new ArrayList<String>();
        valueString.addAll(nSquare.keySet());
        List<Map<Long, Long>> valueFileId = new ArrayList<Map<Long, Long>>();
        int i = 0;
        for (Map<Long, Long> entry : nSquare.values()) {

            ObjectMapper mapper = new ObjectMapper();
            //SquaresSort squareId = mapper.readValue(valueString.get(i), SquaresSort.class);
            BasicDBObject squareId = (BasicDBObject) JSON.parse(valueString.get(i));

            Map<Long, Double> fileIdDevProb = new HashMap<Long, Double>();//fileId, devProb
            for (Long longFileId : entry.keySet()) {
                double alpha = 0;
                Long x = entry.get(longFileId);

                if (a <= x && x <= b) {
                    alpha = ((double) ((double) x - (double) a) / (double) ((double) b - (double) a));


                } else {
                    alpha = 1;

                }

                if (alpha >= degree) {

                    fileIdDevProb.put(longFileId, alpha);
                    JSONObject obj = new JSONObject();
                    //obj.put("nSquare", squareId.getnSquare());
                    obj.put("squareI", (squareId.get("squareI")));
                    obj.put("squareJ", (squareId.get("squareJ")));
                    obj.put("timeZone", squareId.get("timeZone"));
                    obj.put("weekDay", squareId.get("weekDay"));

                    result.put(obj.toJSONString(), fileIdDevProb);
                }
                {
                    //System.out.println("  < 0.8   ");
                }
            }
            i++;
        }
        // System.out.print(result);
        return result;
    }

    public void createJSONforMesh(Map<String, Map<Long, Double>> nSquare, String endCollectionName) throws IOException {
        // System.out.println(nSquare);
        List<BasicDBObject> result = new ArrayList<BasicDBObject>();
        Map<String, String> meshJSON = new HashMap<String, String>();
        int i = 0;

        List<String> keyList = new ArrayList<String>();

        keyList.addAll(nSquare.keySet());

        //System.out.println(nSquare);

        for (Map<Long, Double> entry : nSquare.values()) {

            ObjectMapper mapper = new ObjectMapper();
            // SquaresSort square = mapper.readValue(keyList.get(i), SquaresSort.class);
            BasicDBObject square = (BasicDBObject) JSON.parse(keyList.get(i));
            int devCount = entry.size();
            double despersion, x1 = 0, x2 = 0;


            List<BasicDBObject> devices = new ArrayList<BasicDBObject>();

            /*средний квадрат отклонений равен
            средней из квадратов значений признака минус квадрат средней.*/
            int j = 0;
            List<Long> keyListFileId = new ArrayList<Long>();
            keyListFileId.addAll(entry.keySet());
            // System.out.println(entry.keySet());
            for (Double affiliation : entry.values()) {
                devices.add(new BasicDBObject()
                        .append("fileId", keyListFileId.get(j))
                        .append("devProb", affiliation));
                System.out.println(devices);
                x1 = x1 + affiliation * affiliation;
                x2 = x2 + affiliation;
                j++;
            }

            x1 = x1 / entry.size();
            x2 = x2 / entry.size();
            despersion = x1 - x2 * x2;

            BasicDBObject meshSquare = new BasicDBObject();
            meshSquare.put("timeZone", square.get("timeZone"));
            meshSquare.put("weekDay", square.get("weekDay"));
            //meshSquare.put("nSquare", square.getnSquare().values().iterator().next());
            meshSquare.put("squareI", square.get("squareI"));
            meshSquare.put("squareJ", square.get("squareJ"));
            meshSquare.put("devCount", devCount);
            meshSquare.put("despersion", despersion);
            meshSquare.put("devices", devices);

            result.add(meshSquare);
            // meshSquare=null;
            System.gc();
            i++;


        }

        Set<BasicDBObject> set = new HashSet<BasicDBObject>(result);
        result.clear();
        result.addAll(set);
        System.out.println(endCollectionName);
        // System.out.println("11111111111111111111111111111111111111");
        // System.out.println(result);
        Mongo mongo = new Mongo("10.130.101.9", 27017);
        // Mongo mongo = new Mongo("127.0.0.1", 27017);
        DB db = mongo.getDB("mototest");
        DBCollection collection = db.getCollection(endCollectionName);
        collection.insert(result);
    }


    /**TODO создать коллекцию сетки*/
}

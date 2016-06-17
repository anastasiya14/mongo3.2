package Mesh.Interfaces.Impl;

import Mesh.Interfaces.SquareSort;
import Mesh.POJOjson.SquaresSort;
import com.mongodb.*;
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

    private static final int a = 1;

    private static final int b = 30;
    //   private static final int b = 18000; //9000 записей за месяц (30 сек)
    private static final double degree = 0.8;

    public Map<String, Map<Long, Long>> numberFileIdinSquare(List<String> nSquare) throws IOException {

        Map<String, Map<Long, Long>> squareNumberFileId = new HashMap<String, Map<Long, Long>>();

        Map<Long, Long> numberFileId = new HashMap<Long, Long>();


        Map<String, Long> numberRepetition = new HashMap<String, Long>();
        Long id = null;

        for (String entryString : nSquare) {

            // System.out.println("----------- " + entryString);

            ObjectMapper mapper = new ObjectMapper();

            SquaresSort squareId = mapper.readValue(entryString, SquaresSort.class);

            if (!numberRepetition.containsKey(entryString)) { //проверяет есть ли в мапе json

                numberRepetition.put(entryString, (long) 1);

            } else {

                numberRepetition.put(entryString, numberRepetition.get(entryString) + 1);
            }


        }
        int i = 0;
        List<Long> valueFileIdAndNumber = new ArrayList<Long>();
        valueFileIdAndNumber.addAll(numberRepetition.values());


        for (String entry : numberRepetition.keySet()) {

            ObjectMapper mapper = new ObjectMapper();

            SquaresSort squareId = mapper.readValue(entry, SquaresSort.class);
            id = squareId.getFileId().values().iterator().next();

            JSONObject obj = new JSONObject();
            obj.put("nSquare", squareId.getnSquare());
            obj.put("squareI", squareId.getSquareI());
            obj.put("squareJ", squareId.getSquareJ());
            obj.put("timeZone", squareId.getTimeZone());
            obj.put("weekDay", squareId.getWeekDay());
            //  System.out.println(" obj = " + obj.toJSONString());

            //System.out.println("fileId = " +squareId.getFileId().values().iterator().next());
            if (squareNumberFileId.containsKey(obj.toJSONString())) {
                if (!squareNumberFileId.containsKey(squareId.getFileId().values().iterator().next())) {

                    Map<Long, Long> fileIdNum = new HashMap<Long, Long>();
                    fileIdNum.putAll(squareNumberFileId.values().iterator().next());

                    fileIdNum.put(squareId.getFileId().values().iterator().next(), valueFileIdAndNumber.get(i));
                    //  System.out.println("n :" + fileIdNum);
                    squareNumberFileId.put(obj.toJSONString(), fileIdNum);
                }
            } else {
                Map<Long, Long> fileIdNum = new HashMap<Long, Long>();
                fileIdNum.put(id, valueFileIdAndNumber.get(i));
                squareNumberFileId.put(obj.toJSONString(), fileIdNum);
            }
            i++;
        }

        // System.out.println("numberRepetition " + numberRepetition);
        // System.out.println("numberList " + nSquare.size());
        // System.out.println("Map<JSON c нужными параметрами,Map<fileID,кол-во повторений>>  " + squareNumberFileId);
        return squareNumberFileId;
    }


    public Map<String, Map<Long, Double>> assessment(Map<String, Map<Long, Long>> nSquare) throws IOException {

        /* оценка записей, удаление лишних*/

        //  System.out.println("nSquare  " + nSquare);

        Map<String, Map<Long, Double>> result = new HashMap<String, Map<Long, Double>>();

        List<String> valueString = new ArrayList<String>();
        valueString.addAll(nSquare.keySet());
        List<Map<Long, Long>> valueFileId = new ArrayList<Map<Long, Long>>();
        int i = 0;
        for (Map<Long, Long> entry : nSquare.values()) {

            ObjectMapper mapper = new ObjectMapper();
            SquaresSort squareId = mapper.readValue(valueString.get(i), SquaresSort.class);


           // valueFileId.addAll(nSquare.values());

            Map<Long, Double> fileIdDevProb = new HashMap<Long, Double>();//fileId, devProb
            for (Long longFileId : entry.keySet()) {
                double alpha = 0;
                Long x = entry.get(longFileId);
                //System.out.println("fileId = " + entry.keySet());
                //System.out.println("x = " + x);
                if (a <= x && x <= b) {
                    alpha = ((double) (x - a) / (double) (b - a));
                    //  System.out.println("alpha = " + alpha);

                } else {
                    alpha = 1;
                    //   System.out.println("alpha = " + alpha);
                }

                if (alpha >= degree) {
                    //System.out.println("alpha = " + alpha);
                    fileIdDevProb.put(longFileId, alpha);
                    JSONObject obj = new JSONObject();
                    obj.put("nSquare", squareId.getnSquare());
                    obj.put("squareI", squareId.getSquareI());
                    obj.put("squareJ", squareId.getSquareJ());
                    obj.put("timeZone", squareId.getTimeZone());
                    obj.put("weekDay", squareId.getWeekDay());

                    result.put(obj.toJSONString(), fileIdDevProb);
                }
                {
                    //System.out.println("  < 0.8   ");
                }
            }
            i++;
        }

        // System.out.println("Результат  " + result);
        return result;
    }

    // Map<JSON,Map<fileId, devProb>>
    public void createJSONforMesh(Map<String, Map<Long, Double>> nSquare) throws IOException {

        //System.out.println("test " + nSquare);
        List<BasicDBObject> result = new ArrayList<BasicDBObject>();
        Map<String, String> meshJSON = new HashMap<String, String>();
        int i = 0;

        List<String> keyList = new ArrayList<String>();

        keyList.addAll(nSquare.keySet());

        for (Map<Long, Double> entry : nSquare.values()) {

            ObjectMapper mapper = new ObjectMapper();
            SquaresSort square = mapper.readValue(keyList.get(i), SquaresSort.class);
            int devCount = entry.size();
            double despersion, x1 = 0, x2 = 0;


            List<BasicDBObject> devices = new ArrayList<BasicDBObject>();

            /*средний квадрат отклонений равен
            средней из квадратов значений признака минус квадрат средней.*/
            int j = 0;
            List<Long> keyListFileId = new ArrayList<Long>();
            keyListFileId.addAll(entry.keySet());
            for (Double affiliation : entry.values()) {

                // System.out.println(square);
                devices.add(new BasicDBObject()
                        .append("fileId", keyListFileId.get(j))
                        .append("devProb", affiliation));

                x1 = x1 + affiliation * affiliation;
                x2 = x2 + affiliation;
                j++;
            }

            x1 = x1 / entry.size();
            x2 = x2 / entry.size();
            despersion = x1 - x2 * x2;

            // System.out.println("i =  " + square);
            //  System.out.println("j =  " + square.getSquare().get("j"));
            BasicDBObject meshSquare = new BasicDBObject();
            meshSquare.put("timeZone", square.getTimeZone());
            meshSquare.put("weekDay", square.getWeekDay());
            meshSquare.put("nSquare", square.getnSquare().values().iterator().next());
            meshSquare.put("squareI", square.getSquareI().values().iterator().next());
            meshSquare.put("squareJ", square.getSquareJ().values().iterator().next());
            meshSquare.put("devCount", devCount);
            meshSquare.put("despersion", despersion);
            meshSquare.put("devices", devices);

            result.add(meshSquare);


            // meshSquare.clear();
            i++;


        }

        Set<BasicDBObject> set = new HashSet<BasicDBObject>(result);
        result.clear();
        result.addAll(set);

        Mongo mongo = new Mongo("10.130.101.9", 27017);
        DB db = mongo.getDB("moto");
        DBCollection collection = db.getCollection("mesh_test");

        collection.insert(result);


        System.out.println(result.size());
        //  System.out.println(result);
    }


    /**TODO создать коллекцию сетки*/
}

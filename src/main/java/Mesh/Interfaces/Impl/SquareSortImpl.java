package Mesh.Interfaces.Impl;

import Mesh.Interfaces.SquareSort;
import Mesh.POJOjson.SquaresSort;
import com.mongodb.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created: 07.06.16 17:51
 *
 * @author Anastasiya Plotnikova
 */
public class SquareSortImpl implements SquareSort {

    private static final int a = 1;
    private static final int b = 11;
    private static final double degree = 0.8;

    public Map<String, Map<Long, Long>> numberFileIdinSquare(List<List<String>> nSquare) throws IOException {

        Map<String, Map<Long, Long>> squareNumberFileId = new HashMap<String, Map<Long, Long>>();

        for (List<String> entry : nSquare) {

            Map<Long, Long> numberFileId = new HashMap<Long, Long>();
            Long id = null;

            for (String entryString : entry) {

                ObjectMapper mapper = new ObjectMapper();

                SquaresSort squareId = mapper.readValue(entryString, SquaresSort.class);
                id = squareId.getFileId().values().iterator().next();

                if (!numberFileId.containsKey(id)) {

                    numberFileId.put(id, (long) 1);

                } else {

                    numberFileId.put(id,
                            numberFileId.get(id) + 1);
                }
                squareNumberFileId.put(entryString, numberFileId);
            }
            //System.out.println("ddd  " + numberFileId);
        }
        // System.out.println("Map<JSON c нужными параметрами,Map<fileID,кол-во повторений>>  " + squareNumberFileId);
        return squareNumberFileId;
    }

    public Map<String, Map<Long, Double>> assessment(Map<String, Map<Long, Long>> nSquare) throws IOException {

        /* оценка записей, удаление лишних*/

        Map<String, Map<Long, Double>> result = new HashMap<String, Map<Long, Double>>();
        Map<Long, Double> fileIdDevProb = new HashMap<Long, Double>();

        for (String entry : nSquare.keySet()) {
            double alpha = 0;
            String devProb = " }, \"devProb\" : ";

            ObjectMapper mapper = new ObjectMapper();
            SquaresSort squareId = mapper.readValue(entry, SquaresSort.class);

            Long x = nSquare.get(entry).get(squareId.getFileId().values().iterator().next());

            //System.out.println("x = " + x);
            // System.out.println("JSON " + entry);


            if (a < x && x <= b) {
                alpha = ((double) (x - a) / (double) (b - a));
                devProb = devProb + alpha;
                //System.out.println("alphaff = " + alpha);
            } else {
                alpha = 1;
                //System.out.println("alpha = " + alpha);
            }

            if (alpha >= degree) {
                fileIdDevProb.put(squareId.getFileId().values().iterator().next(), alpha);
                String timeZone = " \"timeZone\" : " + squareId.getTimeZone();
                String weekDay = " \"weekDay\" : " + squareId.getWeekDay();
                String squareI = " \"squareI\" : { \"$numberLong\" : " + squareId.getSquareI().values().iterator().next() + " } ";
                String squareJ = " \"squareJ\" : { \"$numberLong\" : " + squareId.getSquareJ().values().iterator().next() + " } ";
                String numSquare = " \"nSquare\" : { \"$numberLong\" : " + squareId.getnSquare().values().iterator().next() + " } ";
                String str = "{" + timeZone + "," + weekDay + "," +
                        squareI + "," + squareJ + "," +
                        numSquare + "}";
                result.put(str, fileIdDevProb);
            }
        }
        //System.out.println("Результат  " + result);
        return result;
    }

    // Map<JSON,Map<fileId, devProb>>
    public void createJSONforMesh(Map<String, Map<Long, Double>> nSquare) throws IOException {

        System.out.println("test " + nSquare);
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
            for (Double affiliation : entry.values()) {

               // System.out.println(square);
                devices.add(new BasicDBObject()
                        .append("fileId", entry.keySet().iterator().next())
                        .append("devProb", entry.values().iterator().next()));

                x1 = x1 + affiliation * affiliation;
                x2 = x2 + affiliation;

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
    }


    /**TODO создать коллекцию сетки*/
}

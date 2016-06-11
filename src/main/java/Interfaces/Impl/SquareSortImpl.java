package Interfaces.Impl;

import Interfaces.SquareSort;
import POJOjson.Devices;
import POJOjson.Mesh;
import POJOjson.SquaresSort;
import com.mongodb.BasicDBObject;
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
    private static final int b = 3;

    public Map<String, Map<Long, Long>> numberFileIdinSquare(List<List<String>> nSquare) throws IOException {

        Map<String, Map<Long, Long>> squareNumberFileId = new HashMap<String, Map<Long, Long>>();
        for (List<String> entry : nSquare) {

            Map<Long, Long> numberFileId = new HashMap<Long, Long>();
            Long id = null;

            for (String entryString : entry) {

                ObjectMapper mapper = new ObjectMapper();
                System.out.println(entry);

                SquaresSort squareId = mapper.readValue(entryString, SquaresSort.class);
                id = squareId.getFileId().values().iterator().next();

                if (!numberFileId.containsKey(id)) {

                    numberFileId.put(id, (long) 1);

                } else {

                    numberFileId.put(squareId.getFileId().values().iterator().next(),
                            numberFileId.get(id) + 1);
                }
                squareNumberFileId.put(entryString, numberFileId);
            }
            //System.out.println("ddd  " + numberFileId);
        }
        System.out.println("Map<JSON c нужными параметрами,Map<fileID,кол-во повторений>>  " + squareNumberFileId);
        return squareNumberFileId;
    }

    public Map<String, Map<Long, Double>> assessment(Map<String, Map<Long, Long>> nSquare) throws IOException {
      /* оценка записей, удаление лишних*/
        //System.out.println("size Map " + nSquare.size());

        Map<String, Map<Long, Double>> result = new HashMap<String, Map<Long, Double>>();
        Map<Long, Double> fileIdDevProb = new HashMap<Long, Double>();

        for (String entry : nSquare.keySet()) {

            String devProb = " }, \"devProb\" : ";

            ObjectMapper mapper = new ObjectMapper();
            SquaresSort squareId = mapper.readValue(entry, SquaresSort.class);

            Long x = nSquare.get(entry).get(squareId.getFileId().values().iterator().next());
            //System.out.println(x);
            // System.out.println(entry);
            double alpha = 0;
            if (a < x & x <= b) {
                alpha = (x - a) / (b - a);
                devProb = devProb + alpha;
            } else {
                alpha = 1;
            }
            if (alpha > 0.8) {
                fileIdDevProb.put(squareId.getFileId().values().iterator().next(), alpha);
                String timeZone = " \"timeZone\" : " + squareId.getTimeZone();
                String weekDay = " \"weekDay\" : " + squareId.getWeekDay();
                String numSquare = " \"nSquare\" : { \"$numberLong\" : " + squareId.getnSquare().values().iterator().next() + " } ";
                String str = "{" + timeZone + "," + weekDay + "," + numSquare + "}";
                result.put(str, fileIdDevProb);
            }
        }
        System.out.println("Результат  " + result);
        return result;
    }

    // Map<JSON,Map<fileId, devProb>>
    public void createJSONforMesh(Map<String, Map<Long, Double>> nSquare) throws IOException {
        /**TODO создать мапы для mongo
         *  */

        Map<String, String> meshJSON = new HashMap<String, String>();
        List<String> resultList = new ArrayList<String>();
        BasicDBObject meshSquare = new BasicDBObject();
        for (Map<Long, Double> entry : nSquare.values()) {

            ObjectMapper mapper = new ObjectMapper();
            SquaresSort square = mapper.readValue(nSquare.keySet().iterator().next(), SquaresSort.class);
            int devCount = entry.size();
            double despersion, x1 = 0, x2 = 0;



            List<BasicDBObject> devices = new ArrayList<BasicDBObject>();



            /*средний квадрат отклонений равен
            средней из квадратов значений признака минус квадрат средней.*/
            for (Double affiliation : entry.values()) {

                devices.add(new BasicDBObject()
                        .append("fileId", entry.keySet().iterator().next())
                        .append("devProb", entry.values().iterator().next()));

                x1 = x1 + affiliation * affiliation;
                x2 = x2 + affiliation;

            }


            x1 = x1 / entry.size();
            x2 = x2 / entry.size();
            despersion = x1 - x2 * x2;


            meshSquare.put("timeZone", square.getTimeZone());
            meshSquare.put("weekDay", square.getWeekDay());
            meshSquare.put("nSquare", square.getnSquare().values().iterator().next());
            meshSquare.put("devCount", devCount);
            meshSquare.put("despersion", despersion);
            meshSquare.put("devices", devices);

            //  result = nSquare.keySet().iterator().next()
            //          .substring(0, nSquare.keySet().iterator().next().length() - 1) + ", \"devProb\" : " + devCount +
            //         ", \"despersion\" : " + despersion
            //         + ", " + devices + " }";


            // resultList.add(result);
        }
        System.out.println(meshSquare);
    }


    /**TODO создать коллекцию сетки*/
}

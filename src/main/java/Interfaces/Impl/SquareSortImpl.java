package Interfaces.Impl;

import Interfaces.SquareSort;
import POJOjson.SquaresSort;
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

    public void assessment(Map<String, Map<Long, Long>> nSquare) throws IOException {
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
                fileIdDevProb.put(squareId.getFileId().values().iterator().next(),alpha);
                String timeZone=" \"timeZone\" : "+squareId.getTimeZone();
                String weekDay=" \"weekDay\" : "+squareId.getTimeZone();
                String numSquare=" \"nSquare\" : { \"$numberLong\" :"+squareId.getnSquare().values().iterator().next()+" } ";
                String str="{"+timeZone+","+weekDay+","+numSquare+"}";
                result.put(str, fileIdDevProb);
            }
        }
        System.out.println("Результат  " + result);
    }

    // Map<JSON,Map<fileId, devProb>>
    public void createJSONforMesh( Map<String, Map<Long, Double>> nSquare) throws IOException {
        /**TODO создать JSON для сетки:
         *  devices: fileId, devProb;
         *  despersion - десперсия
         *  devCount-кол-во устройств в квадрате
         *  */

        /*находим все поля где weekDay, timeZone, nSquare совпадают.
        * Все отличающиеся fileId в map<Long,double>
        *     поместить все в json*/
        Map<String, String> meshJSON = new HashMap<String, String>();

        for (String entry : nSquare.keySet()) {
            ObjectMapper mapper = new ObjectMapper();
            SquaresSort squareId = mapper.readValue(entry, SquaresSort.class);
            //if(squareId.getFileId().values().iterator().next())
        }
    }


    /**TODO создать коллекцию сетки*/
}

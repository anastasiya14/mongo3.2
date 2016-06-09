package Interfaces.Impl;

import Interfaces.SquareSort;
import POJOjson.SquaresSort;
import POJOjson.nSquare;
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
        System.out.println("size Map " + nSquare.size());
        Map<String, Map<Long, Long>> result = new HashMap<String, Map<Long, Long>>();
        for (Map<Long, Long> entry : nSquare.values()) {

            String devProb = " , \"devProb\" : ";

            ObjectMapper mapper = new ObjectMapper();
            //System.out.println(entry);
            String json = nSquare.keySet().iterator().next();
            SquaresSort squareId = mapper.readValue(json, SquaresSort.class);
            squareId.getFileId().values().iterator().next();
            Long x = entry.values().iterator().next();

            double alpha = 0;
            if (1 < x & x < 3000) {
                alpha = (x - a) / (b - a);
                devProb = devProb + alpha + " } }";
            } else {
                alpha = 1;
            }
            if (alpha > 0.8) {
                result.put(json.replace(" } }", devProb), entry);
            }

        }
        System.out.println("ddd  " + result);
    }

    public void createJSONforMesh(Map<String, Map<Long, Long>> nSquare) throws IOException {
        /**TODO создать JSON для сетки:
         *  devices: fileId, devProb;
         *  despersion
         *  devCount
         *  */
    }
}

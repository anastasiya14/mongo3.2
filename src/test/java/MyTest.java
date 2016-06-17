import Mesh.Interfaces.Impl.SquareSortImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import com.mongodb.BasicDBObject;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * Created: 16.06.16 21:40
 *
 * @author Anastasiya Plotnikova
 */

public class MyTest {
    @Test
    public void testTrue() throws Exception {
    /*    SquareSortImpl squareSort = new SquareSortImpl();
        List<String> json = new ArrayList<String>();
        json.add("{ \"fileId\" : { \"$numberLong\" : \"2\" }, \"weekDay\" : 3.0, " +
                "\"timeZone\" : 201.0, \"nSquare\" : { \"$numberLong\" : \"8526381\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        json.add("{ \"fileId\" : { \"$numberLong\" : \"2\" }, \"weekDay\" : 3.0, " +
                "\"timeZone\" : 201.0, \"nSquare\" : { \"$numberLong\" : \"8526381\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");


        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 3.0, " +
                "\"timeZone\" : 201.0, \"nSquare\" : { \"$numberLong\" : \"8526381\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 3.0, " +
                "\"timeZone\" : 201.0, \"nSquare\" : { \"$numberLong\" : \"8526381\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        Map<Long, Long> result = new HashMap<Long, Long>();
        result.put((long) 2, (long) 2);
        result.put((long) 3, (long) 2);

        JSONObject obj = new JSONObject();
        obj.put("nSquare", 8526381);
        obj.put("squareI", 578);
        obj.put("squareJ", 7553);
        obj.put("timeZone", 201);
        obj.put("weekDay", 3);


        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 4.0, " +
                "\"timeZone\" : 201.0, \"nSquare\" : { \"$numberLong\" : \"8526381\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        Map<Long, Long> result1 = new HashMap<Long, Long>();
        result1.put((long) 3, (long) 1);

        JSONObject obj1 = new JSONObject();
        obj.put("nSquare", 8526381);
        obj.put("squareI", 578);
        obj.put("squareJ", 7553);
        obj.put("timeZone", 201);
        obj.put("weekDay", 4);


        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 4.0, " +
                "\"timeZone\" : 201.0, \"nSquare\" : { \"$numberLong\" : \"852638441\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        Map<Long, Long> result2 = new HashMap<Long, Long>();
        result2.put((long) 3, (long) 1);

        JSONObject obj2 = new JSONObject();
        obj.put("nSquare", 852638441);
        obj.put("squareI", 578);
        obj.put("squareJ", 7553);
        obj.put("timeZone", 201);
        obj.put("weekDay", 4);

        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 4.0, " +
                "\"timeZone\" : 251.0, \"nSquare\" : { \"$numberLong\" : \"852638441\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 4.0, " +
                "\"timeZone\" : 251.0, \"nSquare\" : { \"$numberLong\" : \"852638441\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 4.0, " +
                "\"timeZone\" : 251.0, \"nSquare\" : { \"$numberLong\" : \"852638441\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        json.add("{ \"fileId\" : { \"$numberLong\" : \"3\" }, \"weekDay\" : 4.0, " +
                "\"timeZone\" : 251.0, \"nSquare\" : { \"$numberLong\" : \"852638441\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        json.add("{ \"fileId\" : { \"$numberLong\" : \"7\" }, \"weekDay\" : 4.0, " +
                "\"timeZone\" : 251.0, \"nSquare\" : { \"$numberLong\" : \"852638441\" }, " +
                "\"squareI\" : { \"$numberLong\" : \"578\" }, \"squareJ\" : { " +
                "\"$numberLong\" : \"7553\" } }\n");

        Map<Long, Long> result3 = new HashMap<Long, Long>();
        result3.put((long) 3, (long) 4);
        result3.put((long) 7, (long) 1);

        JSONObject obj3 = new JSONObject();
        obj.put("nSquare", 852638441);
        obj.put("squareI", 578);
        obj.put("squareJ", 7553);
        obj.put("timeZone", 251);
        obj.put("weekDay", 4);

        assertEquals(4, squareSort.numberFileIdinSquare(json).size());

        Map<String, Map<Long, Long>> d = squareSort.numberFileIdinSquare(json);
      //  List<String> list=new ArrayList<String>();
     //   list.addAll(d.keySet());
        int i=0;
        for (Map<Long, Long> entry:d.values()) {
            switch (i){case 0:
                assertEquals(result, entry);
                break;
                case 1:assertEquals(result1, entry);

                    break;
                case 2:assertEquals(result2, entry);

                    break;
                case 3:assertEquals(result3, entry);

                    break;
            }
            i++;

        }

        Map<String, Map<Long, Double>> dd = squareSort.assessment(squareSort.numberFileIdinSquare(json));
        int j=0;


        squareSort.createJSONforMesh(dd);
       /* for (Map<Long, Double> entry:dd.values()) {
            switch (j){case 0:
                assertEquals(result, entry);
                break;
                case 1:assertEquals(result1, entry);

                    break;
                case 2:assertEquals(result2, entry);

                    break;
                case 3:assertEquals(result3, entry);

                    break;
            }
           j++;

        }*/
    }


}

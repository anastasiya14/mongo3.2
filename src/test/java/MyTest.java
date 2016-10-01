import Mesh.Interfaces.Impl.SquareSortImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import Mesh.POJOjson.SquaresSort;
import com.mongodb.BasicDBObject;
import org.codehaus.jackson.map.ObjectMapper;
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
        SquareSortImpl squareSort = new SquareSortImpl();
        List<String> json = new ArrayList<String>();
        json.add("{ \"fileId\" : { \"$numberLong\" : \"291\" }, \"weekDay\" : 3.0, \"timeZone\" : 201.0, \"squareI\" : { \"$numberLong\" : \"1\" }, \"squareJ\" : { \"$numberLong\" : \"3781\" } }\n");

        json.add("{ \"fileId\" : { \"$numberLong\" : \"2922\" }, \"weekDay\" : 3.0, \"timeZone\" : 201.0, \"squareI\" : { \"$numberLong\" : \"1\" }, \"squareJ\" : { \"$numberLong\" : \"3781\" } }\n");
        int i = 0;
        Map<String, Map<Long, Long>> gg = new HashMap<String, Map<Long, Long>>();
        Map<String, Long> res1 = new HashMap<String, Long>();
        JSONObject obj1 = new JSONObject();
        Map<Long, Long> test = new HashMap<Long, Long>();
        for (String entry : json) {

            ObjectMapper mapper = new ObjectMapper();
            SquaresSort squareId = mapper.readValue(entry, SquaresSort.class);

            JSONObject obj = new JSONObject();
           // obj.put("nSquare", squareId.getnSquare());
            obj.put("squareI", squareId.getSquareI());
            obj.put("squareJ", squareId.getSquareJ());
            obj.put("fileId", squareId.getFileId());
            obj.put("timeZone", squareId.getTimeZone());
            obj.put("weekDay", squareId.getWeekDay());


          //  obj1.put("nSquare", squareId.getnSquare());
            obj1.put("squareI", squareId.getSquareI());
            obj1.put("squareJ", squareId.getSquareJ());
            obj1.put("timeZone", squareId.getTimeZone());
            obj1.put("weekDay", squareId.getWeekDay());


            System.out.println(obj.toJSONString());

            if (i == 0) {

                test.put((long) 2, (long) 2);
                gg.put(obj1.toJSONString(), test);
                res1.put(obj.toJSONString(), (long) 2);

            } else {

                test.put((long) 3, (long) 5);
                gg.put(obj1.toJSONString(), test);

                res1.put(obj.toJSONString(), (long) 5);
            }
            i++;

        }
        System.out.println("+++++++++++++++++++++++++++++++"+res1);

        Map<String, Map<Long, Long>> d = squareSort.numberFileIdinSquare(res1);

        System.out.println(d);

        //System.out.println();
        squareSort.createJSONforMesh(squareSort.assessment(d),"test");
        assertEquals(gg, d);


        // Map<String, Map<Long, Double>> dd=new HashMap<String, Map<Long, Double>>();
        // assertEquals(dd,squareSort.assessment(d));
        // System.out.println(squareSort.assessment(d)+" = "+dd);


       /* Map<String, Map<Long, Double>> dd=new HashMap<String, Map<Long, Double>>();
        Map<Long, Double> assessment=new HashMap<Long, Double>();
        assessment.put((long)2,1.0);
        assessment.put((long)3,1.0);
        dd.put(obj1.toJSONString(),assessment);
         assertEquals(dd,squareSort.assessment(d));
         System.out.println(squareSort.assessment(d)+" = "+dd);*/

       /* Map<String, Map<Long, Double>> dd=new HashMap<String, Map<Long, Double>>();
        Map<Long, Double> assessment=new HashMap<Long, Double>();
        assessment.put((long)2, (double)(((double)2-(double)1)/((double)2.2-(double)1)));
        assessment.put((long)3,1.0);
        dd.put(obj1.toJSONString(),assessment);
        assertEquals(dd,squareSort.assessment(d));
        System.out.println(squareSort.assessment(d)+" = "+dd);*/

    }


}

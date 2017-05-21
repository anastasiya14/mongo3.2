import static org.junit.Assert.assertEquals;

import org.bson.Document;
import org.junit.Test;

/**
 * Created: 16.06.16 21:40
 *
 * @author Anastasiya Plotnikova
 */

public class MyTest {
    @Test
    public void testTrue() throws Exception {

        Document test = new Document();
        Document square = new Document("squareJ", "$squareJ");
        square.put("squareI", "$squareI");
        test.put("$group",new Document("_id",square));

        System.out.println(test.toJson());
    }


}

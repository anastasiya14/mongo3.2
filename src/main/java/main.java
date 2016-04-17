import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created: 28.03.16 14:38
 *
 * @author Anastasiya Plotnikova
 */
public class main {
    private static Logger logger = Logger.getLogger(String.valueOf(main.class));

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

 /*
  Получаем JSON и конвертируем его в Java объекты
 */
        MongoData mongoData = null;
        //  try {
        //       mongoData = mapper.readValue(new File("/home/anastasiya/testdata_low.json"), MongoData.class);
        //   } catch (JsonGenerationException e) {
        //      logger.info("Error : " + e.getMessage());
        //  }
        //   System.out.println(mongoData.toString());
        //   System.out.println(mongoData.getGentime());

        WorkWithMongo work = new WorkWithMongo();
        work.MongoConnect();

    }
}
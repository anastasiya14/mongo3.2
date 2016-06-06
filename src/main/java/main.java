import Interfaces.FilterSubMesh;
import Interfaces.Impl.FilterSubMeshImpl;
import Interfaces.Impl.NumberSquareAllImpl;
import Interfaces.NumbersSquareAll;

import POJOjson.MongoData;
import org.codehaus.jackson.map.ObjectMapper;

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


        // 276176202742830L
        //  work.MongoConnect(1294579,276176202742830L,42.411171,-83.874336,45.411171,-82.874336,129454);

        //  FileIdAllImpl fileIdAll = new FileIdAllImpl();
        //  System.out.println(fileIdAll.FindFileId());

        // work.MongoConnect(fileIdAll.FindFileId());
        NumbersSquareAll d = new NumberSquareAllImpl();

        FilterSubMesh df=new FilterSubMeshImpl();
        df.filterSubMesh(d.findSquareAllId());
    }
}

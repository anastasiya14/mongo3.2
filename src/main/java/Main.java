

import Mesh.Interfaces.FilterSubMesh;
import Mesh.Interfaces.Impl.FilterSubMeshImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created: 28.03.16 14:38
 *
 * @author Anastasiya Plotnikova
 */
public class Main {
    private static Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) throws Exception {

        final List<String> startCollectionList = new ArrayList<String>();
        startCollectionList.add("preMesh_traffic_1");
        //startCollectionList.add("testMesh");
        startCollectionList.add("preMesh_traffic_2");
        startCollectionList.add("preMesh_traffic_3");
        startCollectionList.add("preMesh_traffic_4");
        startCollectionList.add("preMesh_traffic_5");
        startCollectionList.add("preMesh_traffic_6");
        startCollectionList.add("preMesh_traffic_7");


        final List<String> endCollectionList = new ArrayList<String>();
        endCollectionList.add("mesh_1day");
        endCollectionList.add("mesh_2day");
        endCollectionList.add("mesh_3day");
        endCollectionList.add("mesh_4day");
        endCollectionList.add("mesh_5day");
        endCollectionList.add("mesh_6day");
        endCollectionList.add("mesh_7day");

        //последовательное выполнение
    /*    int i = 0;
        for (String entry : startCollectionList) {
            System.out.println(entry);
            System.out.println(endCollectionList.get(i));
            try {
                squareSort.createJSONforMesh(squareSort
                        .assessment(squareSort
                                .numberFileIdinSquare(filterSubMesh
                                        .filterSubMesh(entry))), endCollectionList.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }*/

        ExecutorService executor = Executors.newFixedThreadPool(5);

        int i = 0;
        for (String entry : startCollectionList) {
            Runnable worker = new Parallel(entry, endCollectionList.get(i));
            executor.execute(worker);
            i++;
        }
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {

        }
        System.out.println("\nFinished all threads");
    }
}


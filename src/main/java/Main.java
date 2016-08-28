

import Mesh.Interfaces.FilterSubMesh;
import Mesh.Interfaces.Impl.FilterSubMeshImpl;
import Mesh.Interfaces.Impl.SquareSortImpl;
import routing.Impl.JSONtoDataImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Created: 28.03.16 14:38
 *
 * @author Anastasiya Plotnikova
 */
public class Main {
    private static Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) throws Exception {

        WorkWithMongo work = new WorkWithMongo();

        final FilterSubMesh filterSubMesh = new FilterSubMeshImpl();
        final SquareSortImpl squareSort = new SquareSortImpl();

        final List<String> startCollectionList = new ArrayList<String>();
        startCollectionList.add("preMesh_traffic_1");
        startCollectionList.add("preMesh_traffic_2");
        startCollectionList.add("preMesh_traffic_3");
        startCollectionList.add("preMesh_traffic_4");
        startCollectionList.add("preMesh_traffic_5");
        startCollectionList.add("preMesh_traffic_6");
        startCollectionList.add("preMesh_traffic_7");


        final List<String> endCollectionList = new ArrayList<String>();
        endCollectionList.add("mesh_1");
        endCollectionList.add("mesh_2");
        endCollectionList.add("mesh_3");
        endCollectionList.add("mesh_4");
        endCollectionList.add("mesh_5");
        endCollectionList.add("mesh_6");
        endCollectionList.add("mesh_7");

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

        ExecutorService executor = Executors.newFixedThreadPool(7);

        int i = 0;
        for (String entry : startCollectionList) {
            Runnable worker = new MyRunnable(entry, endCollectionList.get(i));
            executor.execute(worker);
            i++;
        }
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {

        }
        System.out.println("\nFinished all threads");

        // squareSort.numberFileIdinSquare(filterSubMesh.filterSubMesh());

        // JSONtoDataImpl jsoNtoData = new JSONtoDataImpl();
        // jsoNtoData.jsonToData();
    }
}

class NewThread implements Runnable {

    public NewThread(String startCollectionName, String endCollectionName) throws IOException {

        final FilterSubMesh filterSubMesh = new FilterSubMeshImpl();
        final SquareSortImpl squareSort = new SquareSortImpl();

        squareSort.createJSONforMesh(squareSort
                .assessment(squareSort
                        .numberFileIdinSquare(filterSubMesh
                                .filterSubMesh(startCollectionName))), endCollectionName);

    }

    public void run() {

    }


}

class MyRunnable implements Runnable {
    private String startCollectionName;
    private String endCollectionName;

    MyRunnable(String startCollectionName, String endCollectionName) {
        this.startCollectionName = startCollectionName;
        this.endCollectionName = endCollectionName;
    }


    public void run() {
        final FilterSubMesh filterSubMesh = new FilterSubMeshImpl();
        final SquareSortImpl squareSort = new SquareSortImpl();

        try {
            squareSort.createJSONforMesh(squareSort
                    .assessment(squareSort
                            .numberFileIdinSquare(filterSubMesh
                                    .filterSubMesh(startCollectionName))), endCollectionName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
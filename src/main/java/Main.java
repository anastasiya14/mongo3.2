

import Mesh.Interfaces.FilterSubMesh;
import Mesh.Interfaces.Impl.FilterSubMeshImpl;
import Mesh.Interfaces.Impl.SquareSortImpl;
import routing.Impl.JSONtoDataImpl;

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

        FilterSubMesh filterSubMesh = new FilterSubMeshImpl();
        SquareSortImpl squareSort = new SquareSortImpl();

        squareSort.createJSONforMesh(squareSort.assessment(squareSort.numberFileIdinSquare(filterSubMesh.filterSubMesh())));


        // squareSort.numberFileIdinSquare(filterSubMesh.filterSubMesh());

        // JSONtoDataImpl jsoNtoData = new JSONtoDataImpl();
        // jsoNtoData.jsonToData();
    }
}

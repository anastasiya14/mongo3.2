package Mesh.Interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created: 07.06.16 17:47
 *
 * @author Anastasiya Plotnikova
 */
public interface SquareSort {

    public Map<String, Map<Long, Long>> numberFileIdinSquare(List<String> nSquare) throws IOException;

    public Map<String, Map<Long, Double>> assessment(Map<String, Map<Long, Long>> nSquare) throws IOException;

    public void createJSONforMesh(Map<String, Map<Long, Double>> nSquare) throws IOException;
}

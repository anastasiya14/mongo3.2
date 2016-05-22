import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

/**
 * Created: 22.05.16 12:07
 *
 * @author Anastasiya Plotnikova
 */
public interface FileIdAll {

    public List<String> FindFileId() throws UnknownHostException; // потом можно добавить на вход параметры подкл к бд

    public Set<String> filterList(List<String> listFileId); //удалит все дубликаты

}

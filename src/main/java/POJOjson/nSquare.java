package POJOjson;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created: 06.06.16 9:03
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class nSquare{
    Map<String,Long> nSquare=new HashMap<String,Long>();

    public nSquare() {

    }

    public nSquare(Map<String, Long> nSquare) {
        this.nSquare = nSquare;
    }

    public Map<String, Long> getnSquare() {
        return nSquare;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nSquare", nSquare)
                .toString();
    }

    public void setnSquare(Map<String, Long> nSquare) {
        this.nSquare = nSquare;
    }
}

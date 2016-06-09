package POJOjson;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created: 08.06.16 17:45
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SquaresSort {

    private int timeZone;
    private int weekDay;
    Map<String, Long> nSquare = new HashMap<String, Long>();
    Map<String, Long> fileId = new HashMap<String, Long>();

    public SquaresSort() {
    }

    public SquaresSort(int timeZone, Map<String, Long> fileId, Map<String, Long> nSquare, int weekDay) {
        this.timeZone = timeZone;
        this.fileId = fileId;
        this.nSquare = nSquare;
        this.weekDay = weekDay;
    }

    public Map<String, Long> getnSquare() {
        return nSquare;
    }

    public void setnSquare(Map<String, Long> nSquare) {
        this.nSquare = nSquare;
    }

    public Map<String, Long> getFileId() {
        return fileId;
    }

    public void setFileId(Map<String, Long> fileId) {
        this.fileId = fileId;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nSquare", nSquare)
                .append("timeZone", timeZone)
                .append("weekDay", weekDay)
                .append("fileId", fileId)
                .toString();
    }
}

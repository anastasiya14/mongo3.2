package Mesh.POJOjson;

import com.mongodb.BasicDBList;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private double devProb;
    private Map<String, Long> fileId = new HashMap<String, Long>();
    private Map<String, Long> nSquare = new HashMap<String, Long>();
    private  BasicDBList square = new BasicDBList();


    public SquaresSort() {
    }

    public SquaresSort(int timeZone, int weekDay, double devProb, Map<String, Long> fileId, Map<String, Long> nSquare, BasicDBList square) {
        this.timeZone = timeZone;
        this.weekDay = weekDay;
        this.devProb = devProb;
        this.fileId = fileId;
        this.nSquare = nSquare;
        this.square = square;
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

    public double getDevProb() {
        return devProb;
    }

    public void setDevProb(double devProb) {
        this.devProb = devProb;
    }

    public Map<String, Long> getFileId() {
        return fileId;
    }

    public void setFileId(Map<String, Long> fileId) {
        this.fileId = fileId;
    }

    public Map<String, Long> getnSquare() {
        return nSquare;
    }

    public void setnSquare(Map<String, Long> nSquare) {
        this.nSquare = nSquare;
    }

    public BasicDBList getSquare() {
        return square;
    }

    public void setSquare(BasicDBList square) {
        this.square = square;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timeZone", timeZone)
                .append("weekDay", weekDay)
                .append("devProb", devProb)
                .append("fileId", fileId)
                .append("nSquare", nSquare)
                .append("square", square)
                .toString();
    }
}

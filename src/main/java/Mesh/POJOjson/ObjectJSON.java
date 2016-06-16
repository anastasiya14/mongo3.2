package Mesh.POJOjson;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created: 16.06.16 18:01
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectJSON {

    private int timeZone;
    private int weekDay;
    private double devProb;
    private Long fileId;
    private Long nSquare;
    private Long squareI;
    private Long squareJ;


    public ObjectJSON() {
    }

    public ObjectJSON(int timeZone, int weekDay, double devProb, Long fileId, Long nSquare, Long squareI, Long squareJ) {
        this.timeZone = timeZone;
        this.weekDay = weekDay;
        this.devProb = devProb;
        this.fileId = fileId;
        this.nSquare = nSquare;
        this.squareI = squareI;
        this.squareJ = squareJ;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public double getDevProb() {
        return devProb;
    }

    public Long getFileId() {
        return fileId;
    }

    public Long getnSquare() {
        return nSquare;
    }

    public Long getSquareI() {
        return squareI;
    }

    public Long getSquareJ() {
        return squareJ;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timeZone", timeZone)
                .append("weekDay", weekDay)
                .append("devProb", devProb)
                .append("fileId", fileId)
                .append("nSquare", nSquare)
                .append("squareI", squareI)
                .append("squareJ", squareJ)
                .toString();
    }
}

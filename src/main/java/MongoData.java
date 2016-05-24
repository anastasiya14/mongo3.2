import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

/**
 * Created: 28.03.16 14:41
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MongoData {

    public MongoData() {
    }

    public MongoData(long fileId, double weekDay, double dayTime, List loc) {
        this.fileId = fileId;
        this.weekDay = weekDay;
        this.dayTime = dayTime;
        this.loc = loc;
    }

    //private Map<String, String> _id;
    private long fileId; //Integer
    private double dayTime;
    private double weekDay;
    private List loc;

    public List getLoc() {
        return loc;
    }

    public void setLoc(List loc) {
        this.loc = loc;
    }

    public double getDayTime() {
        return dayTime;
    }

    public void setDayTime(double dayTime) {
        this.dayTime = dayTime;
    }

    public double getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(double weekDay) {
        this.weekDay = weekDay;
    }


    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fileId", fileId)
                .append("dayTime", dayTime)
                .append("weekDay", weekDay)
                .append("loc", loc)
                .toString();
    }
}

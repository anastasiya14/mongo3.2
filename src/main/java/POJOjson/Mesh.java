package POJOjson;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created: 07.06.16 17:56
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mesh {
    private Long nSquare;
    private Long timeZone;
    private int weekDay;
    private int devCount;
    private double despersion;
    private Devices devices;

    public Mesh() {
    }

    public Mesh(Long nSquare, Long timeZone, int weekDay, int devCount, double despersion, Devices devices) {
        this.nSquare = nSquare;
        this.timeZone = timeZone;
        this.weekDay = weekDay;
        this.devCount = devCount;
        this.despersion = despersion;
        this.devices = devices;
    }

    public Long getnSquare() {
        return nSquare;
    }

    public void setnSquare(Long nSquare) {
        this.nSquare = nSquare;
    }

    public Long getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Long timeZone) {
        this.timeZone = timeZone;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getDevCount() {
        return devCount;
    }

    public void setDevCount(int devCount) {
        this.devCount = devCount;
    }

    public double getDespersion() {
        return despersion;
    }

    public void setDespersion(double despersion) {
        this.despersion = despersion;
    }

    public Devices getDevices() {
        return devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nSquare", nSquare)
                .append("timeZone", timeZone)
                .append("weekDay", weekDay)
                .append("devCount", devCount)
                .append("despersion", despersion)
                .append("devices", devices)
                .toString();
    }
}

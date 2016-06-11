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
    private int timeZone;
    private int weekDay;
    private int devCount;
    private double despersion;
    private Devices devices;

    public Mesh() {
    }

    public Mesh(Long nSquare, Devices devices, double despersion, int weekDay, int timeZone, int devCount) {
        this.nSquare = nSquare;
        this.devices = devices;
        this.despersion = despersion;
        this.weekDay = weekDay;
        this.timeZone = timeZone;
        this.devCount = devCount;
    }

    public Long getnSquare() {
        return nSquare;
    }

    public void setnSquare(Long nSquare) {
        this.nSquare = nSquare;
    }

    public Devices getDevices() {
        return devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }

    public double getDespersion() {
        return despersion;
    }

    public void setDespersion(double despersion) {
        this.despersion = despersion;
    }

    public int getDevCount() {
        return devCount;
    }

    public void setDevCount(int devCount) {
        this.devCount = devCount;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
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

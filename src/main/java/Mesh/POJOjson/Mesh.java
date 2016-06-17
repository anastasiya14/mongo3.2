package Mesh.POJOjson;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created: 07.06.16 17:56
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mesh {
    private Map<String, Long> nSquare = new HashMap<String, Long>();
    private Map<String, Long> squareI = new HashMap<String, Long>();
    private Map<String, Long> squareJ = new HashMap<String, Long>();
    private int timeZone;
    private int weekDay;
    private int devCount;
    private double despersion;
    private List<Devices> devices = new ArrayList<Devices>();

    public Mesh() {
    }

    public Mesh(Map<String, Long> nSquare, Map<String, Long> squareI, Map<String, Long> squareJ, int timeZone, int weekDay, int devCount, double despersion, List<Devices> devices) {
        this.nSquare = nSquare;
        this.squareI = squareI;
        this.squareJ = squareJ;
        this.timeZone = timeZone;
        this.weekDay = weekDay;
        this.devCount = devCount;
        this.despersion = despersion;
        this.devices = devices;
    }

    public Map<String, Long> getnSquare() {
        return nSquare;
    }

    public void setnSquare(Map<String, Long> nSquare) {
        this.nSquare = nSquare;
    }

    public Map<String, Long> getSquareI() {
        return squareI;
    }

    public void setSquareI(Map<String, Long> squareI) {
        this.squareI = squareI;
    }

    public Map<String, Long> getSquareJ() {
        return squareJ;
    }

    public void setSquareJ(Map<String, Long> squareJ) {
        this.squareJ = squareJ;
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

    public List<Devices> getDevices() {
        return devices;
    }

    public void setDevices(List<Devices> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nSquare", nSquare)
                .append("squareI", squareI)
                .append("squareJ", squareJ)
                .append("timeZone", timeZone)
                .append("weekDay", weekDay)
                .append("devCount", devCount)
                .append("despersion", despersion)
                .append("devices", devices)
                .toString();
    }
}

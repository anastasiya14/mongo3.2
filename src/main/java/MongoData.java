import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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

    public MongoData(int confidence, double speed, float radiusOfCurve, float longitude,
                     double heading, long gentime, long fileld, float latitude) {
        Confidence = Confidence;
        Speed = speed;
        RadiusOfCurve = radiusOfCurve;
        Longitude = longitude;
        Heading = heading;
        Gentime = gentime;
        Fileld = fileld;
        Latitude = latitude;
    }

    private Map<String, String> _id;
    private double Speed; //Real m/sec
    private int Confidence; //Integer
    private long Fileld; //Integer
    private long Gentime; //Integer
    private double Heading; //Real Degrees
    private float Latitude; //Float
    private float Longitude; //Float
    private float RadiusOfCurve; //Float


    public int getConfidence() {
        return Confidence;
    }

    public void setConfidence(int confidence) {
        Confidence = confidence;
    }

    public Map<String, String> get_id() {
        return _id;
    }

    public void set_id(Map<String, String> _id) {
        this._id = _id;
    }

    public long getFileld() {
        return Fileld;
    }

    public void setFileld(long fileld) {
        Fileld = fileld;
    }

    public long getGentime() {
        return Gentime;
    }

    public void setGentime(long gentime) {
        Gentime = gentime;
    }

    public double getHeading() {
        return Heading;
    }

    public void setHeading(double heading) {
        Heading = heading;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public float getRadiusOfCurve() {
        return RadiusOfCurve;
    }

    public void setRadiusOfCurve(float radiusOfCurve) {
        RadiusOfCurve = radiusOfCurve;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Confidence", Confidence)
                .append("Fileld", Fileld)
                .append("Gentime", Gentime)
                .append("Heading", Heading)
                .append("Latitude", Latitude)
                .append("Longitude", Longitude)
                .append("RadiusOfCurve", RadiusOfCurve)
                .append("Speed", Speed)
                .append("_id", _id)
                .toString();
    }
}

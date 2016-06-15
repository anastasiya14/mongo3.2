package Mesh.POJOjson;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created: 07.06.16 17:59
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Devices {
    private Map<String, Long> fileId = new HashMap<String, Long>();
    private double devProb;

    public Devices() {
    }

    public Devices(double devProb, Map<String, Long> fileId) {
        this.devProb = devProb;
        this.fileId = fileId;
    }

    public Map<String, Long> getFileId() {
        return fileId;
    }

    public void setFileId(Map<String, Long> fileId) {
        this.fileId = fileId;
    }

    public double getDevProb() {
        return devProb;
    }

    public void setDevProb(double devProb) {
        this.devProb = devProb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fileId", fileId)
                .append("devProb", devProb)
                .toString();
    }
}

package POJOjson;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created: 07.06.16 17:59
 *
 * @author Anastasiya Plotnikova
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Devices {
    private Long fileId;
    private double devProb;

    public Devices() {
    }

    public Devices(Long fileId, double devProb) {
        this.fileId = fileId;
        this.devProb = devProb;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
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

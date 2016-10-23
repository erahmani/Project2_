package UI.terminal;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by DotinSchool2 on 9/7/2016.
 */
@XmlRootElement
class OutLog {
    private String path;

    @XmlAttribute(name = "path")
    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Out Log path: "+ path;
    }
}

package UI.terminal;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by DotinSchool2 on 9/7/2016.
 */
@XmlRootElement

class Server {
    private String ip;
    private Integer port;

    @XmlAttribute(name="ip")
    private void setIp(String ip) {
        this.ip = ip;
    }

    @XmlAttribute(name = "port")
    private void setPort(Integer port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Server ip: "+ip+" Server port: "+ port +"\n";
    }
}

package Business;

import java.io.Serializable;

public class Campus implements Serializable {
    private int port;
    private String code;
    private String name;

    public Campus(String name, int port, String code) {
        this.name = name;
        this.port = port;
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public int getPort() {
        return this.port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}

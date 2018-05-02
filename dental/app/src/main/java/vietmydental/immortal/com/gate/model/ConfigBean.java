package vietmydental.immortal.com.gate.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ConfigBean implements Serializable {
    /** Id */
    protected String id;
    /** Name */
    protected String name;
    /** Data */
    private ArrayList<ConfigBean> data;

    /**
     * Default constructor.
     */
    public ConfigBean() {
        this.id = "";
        this.name = "";
        this.data = new ArrayList<>();
    }
    /**
     * Constructor.
     * @param id Id
     * @param name Name
     */
    public ConfigBean(String id, String name) {
        this.id = id;
        this.name = name;
        this.data = new ArrayList<>();
    }
    /**
     * Constructor.
     * @param id Id
     * @param name Name
     * @param data Data
     */
    public ConfigBean(String id, String name, ArrayList<ConfigBean> data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    /**
     * Get id.
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * Get id (Integer value).
     * @return Id
     */
    public int getIdInt() {
        try {
            return Integer.valueOf(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get name.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Get data.
     * @return Data
     */
    public ArrayList<ConfigBean> getData() {
        return data;
    }
}

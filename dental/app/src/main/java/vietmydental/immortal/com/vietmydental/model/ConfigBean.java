package vietmydental.immortal.com.vietmydental.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nguyenpt on 4/16/18.
 */

public class ConfigBean implements Serializable {
    /** Id */
    private String id;
    /** Name */
    private String name;
    /** Data */
    private ArrayList<ConfigBean> data;

    /**
     * Constructor.
     * @param id Id
     * @param name Name
     */
    public ConfigBean(String id, String name) {
        this.id = id;
        this.name = name;
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

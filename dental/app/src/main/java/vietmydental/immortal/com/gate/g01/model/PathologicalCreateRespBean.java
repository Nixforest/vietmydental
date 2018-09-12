package vietmydental.immortal.com.gate.g01.model;

import com.google.gson.JsonObject;

import vietmydental.immortal.com.gate.model.ConfigExtBean;

public class PathologicalCreateRespBean {
    /** List information */
    private ConfigExtBean bean = new ConfigExtBean();

    public PathologicalCreateRespBean(JsonObject gsonObject) {
        super();
        try {
            bean = new ConfigExtBean(gsonObject.getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConfigExtBean getBean() {
        return bean;
    }
}

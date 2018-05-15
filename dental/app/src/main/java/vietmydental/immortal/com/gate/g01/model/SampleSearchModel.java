package vietmydental.immortal.com.gate.g01.model;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SampleSearchModel implements Searchable {
    private String mTitle;

    public SampleSearchModel(String title) {
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public SampleSearchModel setTitle(String title) {
        mTitle = title;
        return this;
    }
    /**
     * Set name
     * @param name Name value
     */
    public void setName(String name) {

    }

    @Override
    public void setId(String id) {
    }
}
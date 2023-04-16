package com.yart.literule.core.model.data;

import java.util.Objects;

public class DataValue {
    private String id;
    private Object value;
    private String name;
    private Throwable error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Throwable getError() {
        return error;
    }

    public boolean hasError() {
        return Objects.nonNull(error);
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}

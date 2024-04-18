package com.fabrica.hutchisonspring.repository.summary;

import java.io.Serializable;

public class ObjectSelect<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long counter;

    private T attribute;

    public ObjectSelect() {
    }

    public ObjectSelect(Long counter, T attribute) {
        this.counter = counter;
        this.attribute = attribute;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public T getAttribute() {
        return attribute;
    }

    public void setAttribute(T attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "ObjectSelect{" +
                "counter=" + counter +
                ", attribute=" + attribute +
                '}';
    }
}

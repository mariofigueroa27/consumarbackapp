package com.fabrica.hutchisonspring.utils.vm;

import java.util.Set;

public class ValidatorItem<T> {

    private T item;

    private Set<String> errors;

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ValidatorItem{" +
                "item=" + item +
                ", errors='" + errors + '\'' +
                '}';
    }
}

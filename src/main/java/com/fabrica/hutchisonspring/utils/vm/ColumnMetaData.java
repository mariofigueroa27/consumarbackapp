package com.fabrica.hutchisonspring.utils.vm;

public class ColumnMetaData {
    private int pointInSource;

    private String name;

    private int jdbcType;

    private int precision;

    private int scale;

    public ColumnMetaData(int pointInSource, String name, int jdbcType, int precision, int scale) {
        this.pointInSource = pointInSource;
        this.name = name;
        this.jdbcType = jdbcType;
        this.precision = precision;
        this.scale = scale;
    }

    public int getPointInSource() {
        return pointInSource;
    }

    public void setPointInSource(int pointInSource) {
        this.pointInSource = pointInSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "ColumnMetaData{" +
                "pointInSource=" + pointInSource +
                ", name='" + name + '\'' +
                ", jdbcType=" + jdbcType +
                ", precision=" + precision +
                ", scale=" + scale +
                '}';
    }
}

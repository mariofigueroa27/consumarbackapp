package com.fabrica.hutchisonspring.repository.summary;

import java.util.Date;

public class DateWorkingDaySelect {

    private Long counter;

    private Date date;

    private String workingDay;

    public DateWorkingDaySelect() {
    }

    public DateWorkingDaySelect(Long counter, Date date, String workingDay) {
        this.counter = counter;
        this.date = date;
        this.workingDay = workingDay;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(String workingDay) {
        this.workingDay = workingDay;
    }

    @Override
    public String toString() {
        return "DateWorkingDaySelect{" +
                "counter=" + counter +
                ", date=" + date +
                ", workingDay='" + workingDay + '\'' +
                '}';
    }
}

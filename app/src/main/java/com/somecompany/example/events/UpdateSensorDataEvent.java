package com.somecompany.example.events;

public class UpdateSensorDataEvent {
    String text;
    public UpdateSensorDataEvent(String text){
        this.text = text;
    }
    public String getString() {
        return text;
    }
}

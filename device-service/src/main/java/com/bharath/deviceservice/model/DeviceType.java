package com.bharath.deviceservice.model;


public enum DeviceType {
    SPEAKER("SPEAKER"),
    CAMERA("CAMERA"),
    THERMOSTAT("THERMOSTAT"),
    LIGHT("LIGHT"),
    LOCK("LOCK"),
    DOORBELL("DOORBELL"),
    WATER_SENSOR("Water Sensor"),
    SMART_PLUG("Smart Plug"),
    MOTION_SENSOR("Motion Sensor"),
    ELECTRIC_METER("Electric Meter"); // Added this to match the new error

    private final String dbValue;

    DeviceType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    // Helper method to resolve enum from DB string flexibly
    public static DeviceType fromString(String text) {
        if (text == null) return null;
        for (DeviceType b : DeviceType.values()) {
            if (b.dbValue.equalsIgnoreCase(text) || b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No enum constant found for: " + text);
    }
}

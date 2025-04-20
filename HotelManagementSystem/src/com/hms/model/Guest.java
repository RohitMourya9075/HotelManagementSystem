package com.hms.model;

public class Guest {
    private String name;
    private String age;
    private String mobile;
    private String roomNumber;
    private String roomType;

    public Guest(String name, String age, String mobile, String roomNumber, String roomType) {
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    public String toRecordString() {
        // pipe-delimited record for file
        return String.join("|", name, age, mobile, roomNumber, roomType);
    }

    public static Guest fromRecordString(String record) {
        String[] parts = record.split("\\|");
        if (parts.length != 5) return null;
        return new Guest(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    // Getters and setters...
    public String getName() { return name; }
    public String getAge() { return age; }
    public String getMobile() { return mobile; }
    public String getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
}

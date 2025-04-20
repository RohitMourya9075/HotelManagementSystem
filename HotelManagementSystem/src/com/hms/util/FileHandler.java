package com.hms.util;

import com.hms.model.Guest;
import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String DATA_FILE = "data/guests.txt";

    // Ensure data folder and file exist
    static {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();
            File file = new File(DATA_FILE);
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Guest> readAllGuests() {
        List<Guest> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                Guest g = Guest.fromRecordString(line.trim());
                if (g != null) list.add(g);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveAllGuests(List<Guest> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE, false))) {
            for (Guest g : list) {
                pw.println(g.toRecordString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addGuest(Guest g) {
        List<Guest> list = readAllGuests();
        list.add(g);
        saveAllGuests(list);
    }

    public static boolean deleteGuest(String roomNumber) {
        List<Guest> list = readAllGuests();
        boolean removed = list.removeIf(g -> g.getRoomNumber().equals(roomNumber));
        if (removed) saveAllGuests(list);
        return removed;
    }

    public static boolean updateGuest(String roomNumber, Guest updated) {
        List<Guest> list = readAllGuests();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRoomNumber().equals(roomNumber)) {
                list.set(i, updated);
                saveAllGuests(list);
                return true;
            }
        }
        return false;
    }

    public static Guest findGuest(String roomNumber) {
        return readAllGuests()
                .stream()
                .filter(g -> g.getRoomNumber().equals(roomNumber))
                .findFirst()
                .orElse(null);
    }

    public static boolean isRoomAvailable(String roomNumber) {
        return findGuest(roomNumber) == null;
    }
}

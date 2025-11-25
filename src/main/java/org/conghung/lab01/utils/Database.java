package org.conghung.lab01.utils;

import org.conghung.lab01.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {

    public static Map<String, Student> map = new HashMap<>();

    static {
        map.put(getKey(), new Student("SV01", "Nguyen Van A", 9.5, true));
        map.put(getKey(), new Student("SV02", "Tran Thi B", 8, false));
        map.put(getKey(), new Student("SV03", "Le Van C", 6, true));
        map.put(getKey(), new Student("SV04", "Le Van C", 8, false));
    }

    // Lấy key ngẫu nhiên
    public static String getKey() {
        return Integer.toHexString(UUID.randomUUID().toString().hashCode());
    }
}

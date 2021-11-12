package com.example.inclass09;

public class Util {
    public static final String A = "A";
    public static final String B = "B";
    public static final String C = "C";
    public static final String D = "D";
    public static final String F = "F";

    public static double getGradeToInt(String grade) {
        switch (grade) {
            case A:
                return 4.0;
            case B:
                return 3.0;
            case C:
                return 2.0;
            case D:
                return 1.0;
            case F:
                return 0.0;
            default:
                return -1;
        }
    }
}

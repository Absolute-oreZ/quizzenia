/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author User
 */
public class DurationUtil {

    public static String formatDuration(int durationMinute) {
        int hours = durationMinute / 60;
        int minutes = durationMinute % 60;
        return String.format("%02d hours %02d minutes", hours, minutes);
    }
}

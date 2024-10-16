package com.heub.selectcourse.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author qqz
 * @date 2024/10/16
 * <p>
 * description
 */
public class TimeRangeChecker {

    public static boolean isWithinSelectionTime(Date startTime, Date endTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return now.isAfter(start) && now.isBefore(end);
    }
}

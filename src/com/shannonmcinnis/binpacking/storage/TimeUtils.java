package com.shannonmcinnis.binpacking.storage;

import java.time.Duration;
import java.time.LocalTime;

public abstract class TimeUtils {
  public static int convertToIndex(IncrementSize inc, LocalTime time) {
    return time.getHour() * (60 / inc.getMins()) + (int) Math.ceil(time.getMinute() / (double) inc.getMins());
  }

  public static LocalTime convertToTime(IncrementSize inc, int index) {
    int minuteBlocks = index % (60 / inc.getMins());
    int minute = minuteBlocks * inc.getMins();
    int hour = (index - minuteBlocks) / (60 / inc.getMins());
    return LocalTime.of(hour, minute);
  }

  public static int getNumBlocks(IncrementSize inc, Duration d) {
    return (int) Math.ceil(d.toMinutes() / (double) inc.getMins());
  }

  public static int getNumBlocks(IncrementSize inc, LocalTime start, LocalTime end) {
    return convertToIndex(inc, end) - convertToIndex(inc, start);
  }

  public static int getNumBlocks(IncrementSize inc, Task task) {
    return getNumBlocks(inc, task.getDuration());
  }
}

package com.shannonmcinnis.binpacking.storage;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Task implements Comparable<Task> {
  private String name;
  private String description;
  private LocalTime start;
  private LocalTime end;
  private Duration duration;

  public Task(String name, String description, LocalTime start, LocalTime end, Duration duration) {
    this.name = name;
    this.description = description;
    this.start = start;
    this.end = end;
    this.duration = duration;
  }

  public Task(String name, Duration duration) {
    this(name, "", null, null, duration);
  }

  public Task(String name, LocalTime start, LocalTime end) {
    this(name, "", start, end, Duration.between(start, end).abs());
  }

  public Task(String name, String description, LocalTime start, LocalTime end) {
    this(name, description, start, end, Duration.between(start, end).abs());
  }

  public Task(String name, String description, Duration duration) {
    this(name, description, null, null, duration);
  }

  public void setStartAndEnd(LocalTime start, LocalTime end) {
    this.start = start;
    this.end = end;
    this.duration = Duration.between(start, end).abs();
  }

  Duration getDuration() {
    return this.duration;
  }

  LocalTime getStart() {
    return this.start;
  }

  LocalTime getEnd() {
    return this.end;
  }

  public int getNumBlocks(IncrementSize inc) {
    return TimeUtils.getNumBlocks(inc, this.start, this.end);
  }

  public int getStartIndex(IncrementSize inc) {
    return TimeUtils.convertToIndex(inc, this.start);
  }

  public int getEndIndex(IncrementSize inc) {
    return TimeUtils.convertToIndex(inc, this.end);
  }

  public boolean hasStartAndEnd() {
    return this.start != null && this.end != null;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Task)) {
      return false;
    }
    Task other = (Task) o;

    return this.compareTo(other) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, start, end, duration);
  }

  @Override
  public int compareTo(Task task) {
    // negative => this < other
    // zero => this = other
    // positive => this > other
    int r = 0;

    if(this.hasStartAndEnd() && task.hasStartAndEnd()) {
      r = this.start.compareTo(task.start);
      if(r == 0) r = this.end.compareTo(task.end);
    } else if(this.hasStartAndEnd()) {
      return -1;
    } else if(task.hasStartAndEnd()) {
      return 1;
    }

    if(r == 0) r = this.name.compareTo(task.name);
    if(r == 0) r = this.duration.compareTo(task.duration);
    if(r == 0) r = this.description.compareTo(task.description);

    return r;
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    ret.append("Name: ");
    ret.append(this.name);
    ret.append("\n");
    ret.append("Description: ");
    ret.append(this.description);
    ret.append("\n");
    ret.append("Length: ");
    ret.append(this.duration.toString());
    ret.append("\n");
    ret.append("Start: ");
    ret.append(this.start == null ? "null" : this.start.toString());
    ret.append("\n");
    ret.append("End: ");
    ret.append(this.end == null ? "null" : this.end.toString());
    ret.append("\n");
    return ret.toString();
  }
}

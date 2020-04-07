package com.shannonmcinnis.binpacking.storage;

import com.shannonmcinnis.binpacking.schedule.Scheduler;

import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;

public class Day {
  public TreeSet<Task> scheduledTasks;
  public TreeSet<Task> unscheduledTasks;

  public IncrementSize incrementSize;

  public Day(IncrementSize incrementSize) {
    this.scheduledTasks = new TreeSet<>(Comparator.naturalOrder());
    this.unscheduledTasks = new TreeSet<>(Comparator.reverseOrder());
    this.incrementSize = incrementSize;
  }

  public Day() {
    this(IncrementSize.FIFTEEN);
  }

  public TreeSet<Task> getAllTasks() {
    TreeSet<Task> ret = new TreeSet<>(Comparator.naturalOrder());
    ret.addAll(this.scheduledTasks);
    ret.addAll(this.unscheduledTasks);
    return ret;
  }

  public boolean addTask(Task t) {
    return t.hasStartAndEnd() ? this.scheduledTasks.add(t) : this.unscheduledTasks.add(t);
  }

  public boolean anyOverlapping() {
    LocalTime lastEnd = LocalTime.MIN;
    for(Task t : scheduledTasks) {
      if(t.getStart().isBefore(lastEnd)) return true;
      lastEnd = t.getEnd();
    }

    return false;
  }

  public void binPack() {
    new Scheduler(this).bestFit();
  }
}

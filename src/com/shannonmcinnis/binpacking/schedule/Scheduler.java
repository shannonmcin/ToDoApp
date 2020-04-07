package com.shannonmcinnis.binpacking.schedule;

import com.shannonmcinnis.binpacking.storage.Day;
import com.shannonmcinnis.binpacking.storage.Task;
import com.shannonmcinnis.binpacking.storage.TimeUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// https://www.geeksforgeeks.org/bin-packing-problem-minimize-number-of-used-bins/

public class Scheduler {
  private Day day;

  private int[] filledBins;

  private ArrayList<Task> unscheduledTaskList;
  private int[] unscheduledTaskSizes;

  public Scheduler(Day day) {
    this.day = day;
    this.filledBins = this.filledToArray();
    this.unscheduledTaskList = new ArrayList<>(day.unscheduledTasks);
    this.unscheduledTaskSizes = this.durationsToSizesArray();
  }

  public int[] filledToArray() {
    return this.toArray(day.scheduledTasks);
  }

  private int[] toArray(Collection<Task> tasks) {
    int[] blocks = new int[24 * (60 / day.incrementSize.getMins())];

    for(Task t : tasks) {
      if(t.hasStartAndEnd()) {
        for(int i = t.getStartIndex(day.incrementSize); i < t.getEndIndex(day.incrementSize); i++) {
          blocks[i] += 1;
        }
      }
    }

    return blocks;
  }

  public int[] durationsToSizesArray() {
    int[] ret = new int[this.unscheduledTaskList.size()];
//    return this.unscheduledTaskList.stream().map(t -> TimeUtils.getNumBlocks(day.incrementSize, t)).collect(Collectors.toList());
    for(int i = 0; i < unscheduledTaskList.size(); i++) {
      ret[i] = TimeUtils.getNumBlocks(day.incrementSize, unscheduledTaskList.get(i));
    }

    return ret;
  }

  public void bestFit() {
    bestFit(filledBins, unscheduledTaskSizes);
  }

  private void bestFit(int[] filledBins, int[] taskSizes) {

    for(int i = 0; i < taskSizes.length; i++) {
//      System.out.println("fitting task " + i + ": ");
//      System.out.println(unscheduledTaskList.get(i));

      int[] startAndEnd = bestFit(filledBins, taskSizes[i]);

      for(int j = startAndEnd[0]; j < startAndEnd[1]; j++) {
        filledBins[j] += 1;
      }

      LocalTime startTime = TimeUtils.convertToTime(day.incrementSize, startAndEnd[0]);
      LocalTime endTime = TimeUtils.convertToTime(day.incrementSize, startAndEnd[1]);

      day.unscheduledTasks.remove(unscheduledTaskList.get(i));
      unscheduledTaskList.get(i).setStartAndEnd(startTime, endTime);
      day.scheduledTasks.add(unscheduledTaskList.get(i));

    }
  }

  private int[] bestFit(int[] filledBins, int taskSize) {
    int firstGreaterZero = firstEvent(filledBins);
    int lastGreaterZero = lastEvent(filledBins);

    int[] ret = bestFit(filledBins, taskSize, firstGreaterZero, lastGreaterZero);

    if(ret[0] == 0 && ret[1] == 0) {
      ret = bestFit(filledBins, taskSize, 0, firstGreaterZero);
    }

    if(ret[0] == 0 && ret[1] == 0) {
      ret = bestFit(filledBins, taskSize, lastGreaterZero, filledBins.length);
    }

    return ret;
  }

  private int[] bestFit(int[] filledBins, int taskSize, int start, int end) {
    int smallestSpace = -1;
    int startIndex = 0, endIndex = 0;

    for(int i = start; i < end; i++) {
//      System.out.println("i: " + i);
      if(filledBins[i] == 0) {
//        System.out.println("filledBins is 0 here");
        int tempEndIndex = filledBins.length - 1;
        for(int j = i + 1; j < filledBins.length; j++) {
//          System.out.println("j: " + j);
          if(filledBins[j] > 0) {
//            System.out.println("filled bins here greater than 0; breaking");
            tempEndIndex = j;
            break;
          }
        }

        int space = tempEndIndex - i;
//        System.out.println("(tempStart, tempEnd), space: (" + i + ", " + tempEndIndex + "), " + space);

        if((smallestSpace < 0 && space >= taskSize) || (space < smallestSpace && space >= taskSize && space > 0)) {
//          System.out.println("smallest space is currently " + smallestSpace + "; replacing");
          smallestSpace = space;
          startIndex = i;
          endIndex = tempEndIndex;
          if(space == taskSize) break;
        }
      }
    }
    return new int[]{startIndex, endIndex};
  }

  private int firstEvent(int[] filledBins) {
    for(int i = 0; i < filledBins.length; i++) {
      if(filledBins[i] > 0) return i;
    }
    return filledBins.length - 1;
  }

  private int lastEvent(int[] filledBins) {
    for(int i = filledBins.length - 1; i >= 0; i--) {
      if(filledBins[i] > 0) return i;
    }
    return 0;
  }

  }
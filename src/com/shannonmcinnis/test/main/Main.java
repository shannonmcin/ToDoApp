package com.shannonmcinnis.test.main;

import com.shannonmcinnis.binpacking.schedule.Scheduler;
import com.shannonmcinnis.binpacking.storage.Day;
import com.shannonmcinnis.binpacking.storage.IncrementSize;
import com.shannonmcinnis.binpacking.storage.Task;

import java.time.Duration;
import java.time.LocalTime;


public class Main {
  public static void main(String[] args) {
    Task fundies = new Task("fundies", "templates for assignment 9", LocalTime.of(10, 0), LocalTime.of(11, 0));
    Task lunch = new Task("lunch", "eat some food bro", LocalTime.of(12, 30), LocalTime.of(13, 0));
    Task minio = new Task("finish minio", "finish cc proxy for nginx ingress gateway", LocalTime.of(13, 30), LocalTime.of(19, 0));
    Task retool = new Task("finish retool", "figure out what's up with retool", LocalTime.of(19, 0), LocalTime.of(21, 0));

    Task laundry = new Task("laundry", Duration.ofMinutes(15));
    Task dishes = new Task("dishes", Duration.ofMinutes(30));
    Task sweep = new Task("sweep", Duration.ofMinutes(5));

//    Task hw = new Task("homework", LocalTime.of(12, 0), LocalTime.of(14, 0));
//    Task work = new Task("work", LocalTime.of(15, 0), LocalTime.of(16, 0));
//    Task cooking = new Task("cooking", LocalTime.of(10, 0), LocalTime.of(11, 0));

//    Task extra = new Task("pls fit", Duration.ofHours(1));

//    Task otherThing = new Task("fit me!", Duration.ofMinutes(15));

    Day day = new Day();
    day.addTask(fundies);
    day.addTask(lunch);
    day.addTask(minio);
    day.addTask(retool);
    day.addTask(laundry);
    day.addTask(dishes);
    day.addTask(sweep);

    for(Task t : day.getAllTasks()) {
      System.out.println(t);
    }
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

    day.binPack();

    for(Task t : day.getAllTasks()) {
      System.out.println(t);
    }
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
  }
}

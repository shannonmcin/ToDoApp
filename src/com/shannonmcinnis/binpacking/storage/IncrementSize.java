package com.shannonmcinnis.binpacking.storage;

public enum IncrementSize {
  FIVE(5), TEN(10), FIFTEEN(15), TWENTY(20), THIRTY(30), SIXTY(60);

  private int mins;

  public int getMins() {
    return mins;
  }

  IncrementSize(int mins) {
    this.mins = mins;
  }
}

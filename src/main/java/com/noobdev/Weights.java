package com.noobdev;

public enum Weights {
  ONE_WORD(1.0d),
  TWO_WORD(1.5d),
  THREE_WORD(2.0d);

  private final Double value;

  Weights(Double value) {
    this.value = value;
  }

  public Double value() {
    return value;
  }
}

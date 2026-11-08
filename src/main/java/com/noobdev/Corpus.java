package com.noobdev;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;

public record Corpus(
  Map<String, Pair<Integer, String>> terms,
  Map<String, List<Double>> similarity
) {
  public Corpus() {
    this(new HashMap<>(), new HashMap<>());
  }
}


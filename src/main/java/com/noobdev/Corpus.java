package com.noobdev;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;
import java.util.function.BiFunction;

public record Corpus(
  Map<String, String> terms,
  Map<String, List<Double>> similarity,
  List<String> definitions,
  BiFunction<String, String, Double> compute
) {
  public Corpus() {
    this(new HashMap<>(), new HashMap<>(), new ArrayList<>(), NLP::nGram);
  }

  public void addTerms(String term, String definition) {
    if (!similarity().containsKey(term)) {
      similarity().put(term, new ArrayList<Double>(terms().size()));
    }
    else {
      return; // already existed
    }
    terms().put(term, definition);
    definitions().add(definition);
    updateRanks();
  }

  private void updateRanks() {
    for (Map.Entry<String, List<Double>> ent : similarity().entrySet()) {
      if (ent.getValue().size() != definitions().size()) {
        IntStream.range(0, definitions().size()).forEach(index -> {
          ent.getValue().add(0d);
        });
      }

      for (Integer index = 0; index < definitions().size(); index++) {
        ent.getValue().set(index, compute.apply(terms().get(ent.getKey()), definitions().get(index)));
      }
    }
  }

  // gets rank from the closest to term definition being the best at front
  public List<Pair<Double, String>> getRanks(String term) {
    int index = definitions().indexOf(terms().get(term));
    List<Pair<Double, String>> sorted = new ArrayList<>();

    for (Map.Entry<String, List<Double>> ent : similarity().entrySet()) {
      sorted.add(new Pair<>(ent.getValue().get(index), ent.getKey()));
    }

    sorted.sort((p1, p2) -> p2.getKey().compareTo(p1.getKey()));
    return sorted;
  }
}


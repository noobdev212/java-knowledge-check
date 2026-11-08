package com.noobdev;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Map;

public final class NLP {

  private static void nGramsMerge(
    ArrayDeque<String> grams,
    HashMap<String, Double> score,
    int capSize
  ) {
    // will optimize in the future :D

    if (grams.size() == 3 && capSize == 3) {
      String first = grams.peekFirst();
      grams.removeFirst();

      score.merge(first, Weights.ONE_WORD.value(), Double::sum);
      score.merge(grams.peekFirst(), Weights.ONE_WORD.value(), Double::sum);
      score.merge(grams.peekLast(), Weights.ONE_WORD.value(), Double::sum);
      score.merge(first + " " + grams.peekFirst(), Weights.TWO_WORD.value(), Double::sum);
      score.merge(grams.peekFirst() + " " + grams.peekLast(), Weights.TWO_WORD.value(), Double::sum);
      score.merge(first + " " + grams.peekFirst() + " " + grams.peekLast(), Weights.THREE_WORD.value(), Double::sum);
    }

    if (grams.size() == 2 && capSize < 3) {
      String first = grams.peekFirst();
      grams.removeFirst();

      score.merge(first, Weights.ONE_WORD.value(), Double::sum);
      score.merge(grams.peekFirst(), Weights.ONE_WORD.value(), Double::sum);
      score.merge(first + " " + grams.peekFirst(), Weights.TWO_WORD.value(), Double::sum);

    }
    else if (grams.size() == 1 && capSize < 3) {
      String first = grams.peekFirst();
      grams.removeFirst();

      score.merge(first, Weights.ONE_WORD.value(), Double::sum);
    }
  }

  public static Double nGram(String word1, String word2) {
    // scoreA computes word1 to word2
    // scoreB computes word2 to word1
    if (word1.equals(word2)) return 1d;
    HashMap<String, Double> scoreA = new HashMap<>();
    HashMap<String, Double> scoreB = new HashMap<>();

    ArrayDeque<String> grams = new ArrayDeque<>();

    System.out.println("---------------");
    for (String word: word1.split("\\s+")) {
      if (!scoreA.containsKey(word)) {
        scoreA.put(word, 0d);
      }
      grams.add(word);
      nGramsMerge(grams, scoreA, 3);
    }

    nGramsMerge(grams, scoreA, 2);
    grams.clear();

    // if word2 will pull the score down using negative weights
    for (String word: word2.split("\\s+")) {
      if (!scoreB.containsKey(word)) {
        scoreB.put(word, 0d);
      }
      grams.add(word);
      nGramsMerge(grams, scoreB, 3);
    }

    // score the differences between words
    nGramsMerge(grams, scoreB, 2);
    Double uniqueA = 0d,
           uniqueB = 0d,
           duplicate = 0d,
           score = 0d,
           distance = 0d;
    for (Map.Entry<String, Double> entry : scoreA.entrySet()) {
      if (scoreB.containsKey(entry.getKey())) {
        duplicate += Math.abs(entry.getValue() - scoreB.get(entry.getKey()));
      }
      else {
        uniqueA += entry.getValue();
      }
    }

    for (Map.Entry<String, Double> entry : scoreB.entrySet()) {
      if (!scoreA.containsKey(entry.getKey())) {
        uniqueB += entry.getValue();
      }
    }

    // scale uniqueA by 2 to skew the result on favor of word A
    distance = Math.abs(2d*uniqueA - uniqueB);
    score = 1d - (duplicate / distance); // 0.01d in case if both words have nothing in common
    System.out.println(word1 + " : " + word2);
    System.out.println(score);
    return score;
  }
}

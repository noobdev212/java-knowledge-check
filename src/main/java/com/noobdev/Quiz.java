package com.noobdev;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;

public class Quiz {
  private Map<String, String> question;
  private Corpus corpus;

  public Quiz(Corpus corpus) {
    this.question = new HashMap<>();
    this.corpus = corpus;
  }

  public void addQuestion(String question, String term) {
    if (!corpus.terms().containsKey(term) || question.equals("")) {
      throw new IllegalArgumentException("Expected term to exist in corpus: " + term);
    }

    this.question.put(question, term);
  }

  public String getAnswer(String question) {
    if (!this.question.containsKey(question)) {
      throw new IllegalArgumentException("Expected question to exist: " + question);
    }
    return this.question.get(question);
  }

  public void addTerm(String term, String definition) {
    if (corpus.terms().containsKey(term) || definition.equals("")) {
      throw new IllegalArgumentException("Expected valid and unique term: " + term);
    }
    corpus.addTerms(term, definition);
  }

  public String getDefinition(String term) {
    if (!corpus.terms().containsKey(term)) {
      throw new IllegalArgumentException("Expected term to exist in corpus: " + term);
    }

    return corpus.terms().get(term);
  }

  public List<String> getSimilarTerm(String term, int count) {
    if (corpus.terms().size() < count || count <= 0) {
      throw new IndexOutOfBoundsException("request count for similar terms far exceeds the given list");
    }

    List<Pair<Double, String>> ranks = corpus.getRanks(term);
    List<String> similar = new ArrayList<>();

    for (int index = 0; index < count; index++) {
      similar.add(ranks.get(index).getValue());
    }

    return similar;
  }
}

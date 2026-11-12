package com.noobdev;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

public class Quiz {
  private Map<String, String> questions;
  private String path;
  private Corpus corpus;
  private String description;

  // may not be useful since path will auto create corpus
  // public Quiz(Corpus corpus) {
  //   questions = new HashMap<>();
  //   this.corpus = corpus;
  // }

  public Quiz(String path) {
    this.path = path;
    questions = new HashMap<>();

    // if config already existed
    // populate questions from previous
    // config
    if (!Path.resExist(path)) {
      corpus = new Corpus();
    }
    else {
      Map<String, Object> config = Path.readConfig(path);
      if (!config.containsKey("questions") || !config.containsKey("answers")) {
        throw new IllegalArgumentException("Failed to initialize Quiz using config: " + path);
      }

      description = (String)config.get("description");
      List<String> keyQuestions = (ArrayList<String>)config.get("questions");
      List<String> keyAnswers = (ArrayList<String>)config.get("answers");
      for (int index = 0; index < keyQuestions.size(); index++) {
        questions.put(keyQuestions.get(index), keyAnswers.get(index));
      }

      corpus = new Corpus(config);
    }
  }

  public void addQuestion(String question, String term) {
    if (!corpus.terms().containsKey(term) || question.equals("")) {
      throw new IllegalArgumentException("Expected term to exist in corpus: " + term);
    }

    questions.put(question, term);
  }

  public String getAnswer(String question) {
    if (!questions.containsKey(question)) {
      throw new IllegalArgumentException("Expected question to exist: " + question);
    }
    return questions.get(question);
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
      similar.add(ranks.get(index).second());
    }

    return similar;
  }

  public String[] getQuestions() {
    return Arrays.stream(questions.keySet().toArray()).map(Object::toString).toArray(String[]::new);
  }

  public void dumpData() {
    Map<String, Object> payload = new HashMap<>();
    payload.put("terms", corpus.terms().keySet().toArray());
    payload.put("definitions", corpus.definitions());
    payload.put("questions", questions.keySet().toArray());
    payload.put("answers", questions.values().toArray());
    payload.put("description", description);
    Path.writeConfig(path, payload);
  }

  public String getDescription() {
    return description;
  }

  @Override()
  public String toString() {
    return "questions: " + questions + ", terms: " + corpus.terms() + ", definitions: " + corpus.definitions();
  }
}

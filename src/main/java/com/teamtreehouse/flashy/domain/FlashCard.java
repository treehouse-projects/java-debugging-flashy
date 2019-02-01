package com.teamtreehouse.flashy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class FlashCard {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String term;
  private String definition;

  protected FlashCard() {
    id = null;
  }

  public FlashCard(String term, String definition) {
    this();
    this.term = term;
    this.definition = definition;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  public String getDefinition() {
    return definition;
  }

  public void setDefinition(String definition) {
    this.definition = definition;
  }

  public Long getId() {
    return id;
  }
}

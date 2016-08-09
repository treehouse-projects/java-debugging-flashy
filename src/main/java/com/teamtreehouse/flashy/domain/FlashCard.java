package com.teamtreehouse.flashy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;


@Entity
public class FlashCard {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Size(min = 2, max = 12)
  private String term;

  @Size(min = 3, max = 50)
  private String definition;

  public FlashCard() {
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

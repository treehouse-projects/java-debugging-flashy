package com.teamtreehouse.flashy.bootstrap;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.repositories.FlashCardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements ApplicationRunner {
  @Autowired
  private FlashCardRepository flashCardRepository;

  public void run(ApplicationArguments args) throws Exception {
    List<FlashCard> cards = new ArrayList<>();
    cards.add(new FlashCard("JDK", "Java Development Kit"));
    cards.add(new FlashCard("YAGNI", "You Ain't Gonna Need It"));
    cards.add(new FlashCard("SDK", "Software Development Kit"));
    cards.add(new FlashCard("Java SE", "Java Standard Edition"));
    cards.add(new FlashCard("Java EE", "Java Enterprise Edition"));
    cards.add(new FlashCard("JRE", "Java Runtime Environment"));
    cards.add(new FlashCard("JCL", "Java Class Library"));
    cards.add(new FlashCard("JVM", "Java Virtual Machine"));
    flashCardRepository.save(cards);
  }

}

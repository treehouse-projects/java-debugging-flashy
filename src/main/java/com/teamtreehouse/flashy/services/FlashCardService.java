package com.teamtreehouse.flashy.services;

import com.teamtreehouse.flashy.domain.FlashCard;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FlashCardService {
  Long getCurrentCount();
  FlashCard getFlashCardById(Long id);
  FlashCard getNextUnseenFlashCard(Collection<Long> seenIds);
  FlashCard getNextFlashCardBasedOnViews(Map<Long, Long> idToViewCounts);
  List<FlashCard> getRandomFlashCards(int i);
  List<FlashCard> findAll();
  FlashCard save(FlashCard flashCard);
  void delete(Long id);
}

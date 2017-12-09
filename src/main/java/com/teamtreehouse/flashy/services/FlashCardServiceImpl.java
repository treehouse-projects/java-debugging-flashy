package com.teamtreehouse.flashy.services;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.repositories.FlashCardDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@Service
public class FlashCardServiceImpl implements FlashCardService {

  @Autowired
  private FlashCardDao flashCardDao;

  @Override
  public Long getCurrentCount() {
    return flashCardDao.count();
  }

  @Override
  public FlashCard getFlashCardById(Long id) {
    return flashCardDao.findOne(id);
  }

  @Override
  public FlashCard getNextUnseenFlashCard(Collection<Long> seenIds) {
    List<FlashCard> unseen;
    if (seenIds.size() > 0) {
      unseen = flashCardDao.findByIdNotIn(seenIds);
    } else {
      unseen = flashCardDao.findAll();
    }

    FlashCard card = null;
    if (unseen.size() > 0) {
      card = unseen.get(new Random().nextInt(unseen.size()));
    }
    return card;
  }

  @Override
  public FlashCard getNextFlashCardBasedOnViews(Map<Long, Long> idToViewCounts) {
    FlashCard card = getNextUnseenFlashCard(idToViewCounts.keySet());
    if (card == null) {
      card = getLeastViewedFlashCard(idToViewCounts);
    }
    return card;
  }

  public FlashCard getLeastViewedFlashCard(Map<Long, Long> idToViewCounts) {
    Long leastViewedId = null;
    List<Map.Entry<Long, Long>> entries = new ArrayList<>(idToViewCounts.entrySet());
    Collections.shuffle(entries);

    return entries.stream()
        .min(Comparator.comparing(Map.Entry::getValue))
        .map(entry -> flashCardDao.findOne(entry.getKey()))
        .orElseThrow(IllegalArgumentException::new);

  }

  @Override
  public List<FlashCard> getRandomFlashCards(int amount) {
    List<FlashCard> cards = flashCardDao.findAll();
    Collections.shuffle(cards);
    return cards.stream()
            .limit(amount)
            .collect(toList());
  }

  @Override
  public List<FlashCard> findAll() {
    return flashCardDao.findAll();
  }

  @Override
  public FlashCard save(FlashCard flashCard) {
    return flashCardDao.save(flashCard);
  }

  @Override
  public void delete(Long id) {
    flashCardDao.delete(id);
  }


}

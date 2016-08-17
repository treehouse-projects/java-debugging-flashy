package com.teamtreehouse.flashy.services;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.repositories.FlashCardRepository;
import org.hibernate.mapping.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class FlashCardServiceImpl implements FlashCardService {
  private FlashCardRepository flashCardRepository;

  @Autowired
  public void setFlashCardRepository(FlashCardRepository flashCardRepository) {
    this.flashCardRepository = flashCardRepository;
  }

  @Override
  public Long getCurrentCount() {
    return flashCardRepository.count();
  }

  @Override
  public FlashCard getFlashCardById(Long id) {
    return flashCardRepository.findOne(id);
  }

  @Override
  public FlashCard getNextUnseenFlashCard(Collection<Long> seenIds) {
    List<FlashCard> unseen;
    if (seenIds.size() > 0) {
      unseen = flashCardRepository.findByIdNotIn(seenIds);
    } else {
      unseen = flashCardRepository.findAll();
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
        .map(entry -> flashCardRepository.findOne(entry.getKey()))
        .orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public List<FlashCard> getRandomFlashCards(int amount) {
    List<FlashCard> cards = flashCardRepository.findAll();
    Collections.shuffle(cards);
    return cards.stream()
            .limit(amount)
            .collect(toList());
  }
}

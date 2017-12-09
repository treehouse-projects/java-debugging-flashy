package com.teamtreehouse.flashy.repositories;

import com.teamtreehouse.flashy.domain.FlashCard;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardDao extends CrudRepository<FlashCard, Long> {
  List<FlashCard> findByIdNotIn(Collection<Long> ids);
  List<FlashCard> findAll();
  FlashCard save(FlashCard flashCard);
  void delete(Long id);
}

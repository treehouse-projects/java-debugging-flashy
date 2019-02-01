package com.teamtreehouse.flashy.repositories;

import com.teamtreehouse.flashy.domain.FlashCard;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface FlashCardRepository extends CrudRepository<FlashCard, Long> {

  List<FlashCard> findByIdNotIn(Collection<Long> ids);

  List<FlashCard> findAll();
}

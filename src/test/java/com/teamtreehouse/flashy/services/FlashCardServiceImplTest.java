package com.teamtreehouse.flashy.services;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.repositories.FlashCardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlashCardServiceImplTest {

  @Mock
  private FlashCardRepository repository;

  @InjectMocks
  private FlashCardService service = new FlashCardServiceImpl();

  @Test
  public void getRandomFlashCards_ShouldReturnTwo() throws Exception {
    List<FlashCard> flashCards = Arrays.asList(
            new FlashCard(),
            new FlashCard()
    );

    when(repository.findAll()).thenReturn(flashCards);

    assertEquals("getRandomFlashCards should return two favorites", 2, service.getRandomFlashCards(2).size());
    verify(repository).findAll();
  }

  @Test
  public void findById_ShouldReturnOne() throws Exception {
    when(repository.findOne(1L)).thenReturn(new FlashCard());
    assertThat(service.getFlashCardById(1L), instanceOf(FlashCard.class));
  }

}
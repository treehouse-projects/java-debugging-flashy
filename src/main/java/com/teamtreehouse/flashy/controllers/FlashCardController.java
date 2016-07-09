package com.teamtreehouse.flashy.controllers;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.services.FlashCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FlashCardController {
  private FlashCardService flashCardService;

  @Autowired
  public void setFlashCardService(FlashCardService flashCardService) {
    this.flashCardService = flashCardService;
  }

  private Map<Long, Long> getCardCounts(HttpServletRequest req) {
    Map<Long, Long> cardCounts = (Map<Long, Long>) req.getSession().getAttribute("cardCounts");
    if (cardCounts == null) {
      cardCounts = new HashMap<>();
      req.getSession().setAttribute("cardCounts", cardCounts);
    }
    return cardCounts;
  }

  @RequestMapping("/flashcard/next")
  public String showNextFlashCard(HttpServletRequest req) {
    Map<Long, Long> cardCounts = getCardCounts(req);
    FlashCard card = flashCardService.getNextFlashCardBasedOnViews(cardCounts);
    return "redirect:/flashcard/" + card.getId();
  }

  @RequestMapping("/flashcard/{id}")
  public String showFlashCard(HttpServletRequest req, @PathVariable Long id, Model model) {
    Map<Long, Long> cardCounts = getCardCounts(req);
    cardCounts.compute(id, (key, val) -> val == null ? 1 : val + 1);
    model.addAttribute("flashCard", flashCardService.getFlashCardById(id));
    model.addAttribute("viewCount", cardCounts.get(id));
    return "flashcard_show";
  }

}

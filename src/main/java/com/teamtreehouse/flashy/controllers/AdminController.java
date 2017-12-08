package com.teamtreehouse.flashy.controllers;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.services.FlashCardService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

  @Autowired
  FlashCardService service;

  @RequestMapping("/admin")
  public String admin(Model model) {
    List<FlashCard> allCards = service.findAll();
    model.addAttribute("cards", allCards);
    return "admin";
  }

  @RequestMapping(path = "/admin/update/{id}", method = RequestMethod.POST)
  public String updateCard(@PathVariable Long id, @RequestParam Map<String, String> requestParams) {
    FlashCard flashCard = service.getFlashCardById(id);
    flashCard.setTerm(requestParams.get("term"));
    flashCard.setDefinition(requestParams.get("definition"));
    service.save(flashCard);
    return "redirect:/admin";
  }

  @RequestMapping(path = "/admin/add")
  public String addCard(@RequestParam Map<String, String> requestParams) {
    FlashCard flashCard = new FlashCard(
        requestParams.get("term"),
        requestParams.get("definition")
    );
    service.save(flashCard);
    return "redirect:/admin";
  }

}

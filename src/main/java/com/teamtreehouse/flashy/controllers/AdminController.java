package com.teamtreehouse.flashy.controllers;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.services.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminController {
  private FlashCardService flashCardService;

  @Autowired
  public void setFlashCardService(FlashCardService flashCardService) {
    this.flashCardService = flashCardService;
  }

  @RequestMapping("/admin")
  public String admin(FlashCard flashCard, Model model) {
    if (!model.containsAttribute("flashCard")) {
      model.addAttribute("flashCard", new FlashCard());
    }
    model.addAttribute("flashCards", flashCardService.findAll());
    return "admin";
  }

  @RequestMapping(value = "/admin", method = RequestMethod.POST)
  public String addFlashCard(@Valid FlashCard flashCard, BindingResult result,
                             RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("flashCard", flashCard);
    } else {
      flashCardService.save(flashCard);
    }
    return "redirect:/admin";
  }


}

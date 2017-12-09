package com.teamtreehouse.flashy.controllers;

import com.teamtreehouse.flashy.domain.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginPost(Model model, HttpServletRequest request) {
    model.addAttribute("user", new User());
    try {
      Object flash = request.getSession().getAttribute("flash");
      model.addAttribute("flash", flash);

      request.getSession().removeAttribute("flash");
    } catch (Exception ex) {
      // It means "flash" session attribute does not exist...
      // do nothing and proceed normally
    }
    return "login";
  }


//  @RequestMapping("/access_denied")
//  public String accessDenied() {
//    return "access_denied";
//  }
}

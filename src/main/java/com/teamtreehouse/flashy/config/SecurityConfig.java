package com.teamtreehouse.flashy.config;

import com.teamtreehouse.flashy.FlashMessage;
import com.teamtreehouse.flashy.FlashMessage.Status;
import com.teamtreehouse.flashy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserService userService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/css/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // security filter chain
    http
      .authorizeRequests() // anything that follows is assumed to be handled by authorization
        // and it's implied that authentication has already happened at this point
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/flashcard/**").hasRole("ADMIN")
        .antMatchers("/flashcard/**").hasRole("USER")
        // all above lines mean any request to these URIs must have the specific role
        .and()
      .formLogin()
        .loginPage("/login") // our login requests need to be sent to the URI "/login" using POST
        .permitAll()
        .successHandler(loginSuccessHandler())
        .failureHandler(loginFailureHandler())
        .and()
      .logout() // our logout requests are sent to the URI "/logout"
        .permitAll()
        .logoutSuccessUrl("/login");
        // could use logoutSuccessHandler() like in the login above

  }

  private AuthenticationSuccessHandler loginSuccessHandler() {
    return (request, response, exception) -> {
      request.getSession().setAttribute(
          "flash",
          new FlashMessage(
              "Successfully logged in.",
              Status.FAILURE
          )
      );
      response.sendRedirect("/");
    };
  }

  private AuthenticationFailureHandler loginFailureHandler() {
    return (request, response, exception) -> {
      request.getSession().setAttribute(
          "flash",
          new FlashMessage(
              "Incorrect username or password. Please try again",
              Status.FAILURE
          )
      );
      response.sendRedirect("/login");
    };
  }
}

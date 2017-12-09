package com.teamtreehouse.flashy.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @Size(min = 8, max = 20)
  private String username;

  @Column(length = 100)
  private String password;

  @Column(nullable = false)
  private boolean enabled;

  // eager initialization required for looping through roles in getAuthorities()
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_role",
      joinColumns = {@JoinColumn(
          name = "user_id",
          referencedColumnName = "id"
      )
    },
      inverseJoinColumns = {@JoinColumn(
          name = "role_id",
          referencedColumnName = "id"
      )
    }
  )
  private List<Role> roles;

  // This is the method that Spring expects to return a list of GrantedAuthority objects.
  // A GrantedAuthority can be a detailed action that a user has permission to perform
  // or can be broad like the roles we are using.
  // We'll stick to broad here, but with additional work we can define roles as user
  // access levels, each level having a predefined list of authorities (as granular as we like)
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role : roles) { // this requires eager initialization otherwise throws exception
      // org.hibernate.LazyInitializationException: failed to lazily initialize a collection of ...
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}

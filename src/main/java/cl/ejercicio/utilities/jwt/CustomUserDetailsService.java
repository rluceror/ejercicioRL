package cl.ejercicio.utilities.jwt;

import cl.ejercicio.data.EjercicioRepository;
import cl.ejercicio.data.dtos.User;
import cl.ejercicio.utilities.jwt.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  private EjercicioRepository users;

  public CustomUserDetailsService(EjercicioRepository users) {
    this.users = users;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    CustomUserDetails customUserDetails = null;

    final User user = users.findByEmail(username);
    if (user != null) {
      customUserDetails =
          new CustomUserDetails(user.getEmail(), user.getPassword(), user.getRoles());
    }

    return customUserDetails;
  }
}

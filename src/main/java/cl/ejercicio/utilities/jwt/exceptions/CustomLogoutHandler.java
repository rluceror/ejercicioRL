package cl.ejercicio.utilities.jwt.exceptions;

import cl.ejercicio.data.EjercicioRepository;
import cl.ejercicio.data.dtos.User;
import cl.ejercicio.utilities.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

import static cl.ejercicio.utilities.AppConstant.NAME_TOKEN_SESSION;

public class CustomLogoutHandler implements LogoutHandler, Serializable {

  private static final long serialVersionUID = -3409865897460953769L;

  private final transient JwtTokenProvider jwtTokenProvider;
  private final transient EjercicioRepository ejercicioRepository;

  public CustomLogoutHandler(
      final JwtTokenProvider jwtTokenProvider, EjercicioRepository ejercicioRepository) {
    this.ejercicioRepository = ejercicioRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String tokenSearch = request.getHeader(NAME_TOKEN_SESSION);
    final User findEmail = ejercicioRepository.findUserByToken(tokenSearch);
    final String updateToken =
        jwtTokenProvider.revocateToken(
            jwtTokenProvider.getUsername(tokenSearch), findEmail.getRoles());
    ejercicioRepository.revocateToken(tokenSearch, updateToken);
  }
}

package cl.ejercicio.usecase.dosignin;

import cl.ejercicio.EjercicioController;
import cl.ejercicio.data.EjercicioRepository;
import cl.ejercicio.usecase.UserStubs;
import cl.ejercicio.EjercicioServiceFacade;
import cl.ejercicio.data.dtos.User;
import cl.ejercicio.usecases.dosignin.SignUpCase;
import cl.ejercicio.usecases.dosignin.models.SignUpResponse;
import cl.ejercicio.usecases.getusers.GetUsersUseCase;
import cl.ejercicio.utilities.exceptions.ExceptionHandlerResponse;
import cl.ejercicio.utilities.jwt.JwtTokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DoSignInTest {

  @Mock private GetUsersUseCase getUsersUseCase;

  @Mock private EjercicioRepository ejercicioRepository;
  @MockBean AuthenticationManager authenticationManager;
  @MockBean JwtTokenProvider jwtTokenProvider;

  private EjercicioController ejercicioController;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(16);
  }

  @Before
  public void setup() throws IOException {

    final SignUpCase signUpCase =
        new SignUpCase(
            authenticationManager, jwtTokenProvider, ejercicioRepository, passwordEncoder());

    final EjercicioServiceFacade globalLogicServiceFacade =
        new EjercicioServiceFacade(signUpCase, getUsersUseCase);

    ejercicioController = new EjercicioController(globalLogicServiceFacade);

    final User user =
        new User(
            UserStubs.getUser().getId(),
            UserStubs.getUser().getName(),
            UserStubs.getUser().getEmail(),
            UserStubs.getUser().getPassword(),
            UserStubs.getUser().getPhones(),
            UserStubs.getUser().getCreated(),
            UserStubs.getUser().getModified(),
            UserStubs.getUser().getLastLogin(),
            UserStubs.getUser().getToken(),
            UserStubs.getUser().getRoles(),
            UserStubs.getUser().isActive());
    when(ejercicioRepository.save(any(User.class))).thenReturn(user);
    when(ejercicioRepository.findByEmail(any(String.class))).thenReturn(user);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(
            new UsernamePasswordAuthenticationToken(
                DoSignInStubs.getDoSignInRequest().getEmail(), DoSignInStubs.getDoSignInRequest().getPassword()));
    when(ejercicioRepository.updateUser(
            any(String.class), any(String.class), any(String.class), any(String.class)))
        .thenReturn(1);
    when(jwtTokenProvider.createToken(any(), any())).thenReturn(DoSignInStubs.getDoSignInResponse().getToken());
  }

  @Test
  public void itShouldDoSignInWhenApiSuccess() throws IOException {
    assertNotNull(DoSignInStubs.getDoSignInRequest());

    final SignUpResponse signUpResponse = ejercicioController.doSignIn(DoSignInStubs.getDoSignInRequest());

    assertNotNull(DoSignInStubs.getDoSignInResponse());
    assertNotNull(signUpResponse);
    assertEquals(
            signUpResponse,
        new SignUpResponse(
            DoSignInStubs.getDoSignInResponse().getId(),
            DoSignInStubs.getDoSignInResponse().getCreated(),
            DoSignInStubs.getDoSignInResponse().getModified(),
            DoSignInStubs.getDoSignInResponse().getLastLogin(),
            DoSignInStubs.getDoSignInResponse().getToken(),
            DoSignInStubs.getDoSignInResponse().isActive()));
  }

  @Test
  public void itShouldDoSignInWhenApiFailure() throws IOException {
    assertNotNull(DoSignInStubs.getDoSignInRequest());
    try {
      ejercicioController.doSignIn(null);
    } catch (NullPointerException ex) {
      assertEquals(new ExceptionHandlerResponse<>(null).getMessage(), ex.getMessage());
    }
  }
}

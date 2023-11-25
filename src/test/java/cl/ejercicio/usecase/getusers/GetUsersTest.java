package cl.ejercicio.usecase.getusers;

import cl.ejercicio.data.EjercicioRepository;
import cl.ejercicio.usecase.UserStubs;
import cl.ejercicio.EjercicioProtectedController;
import cl.ejercicio.EjercicioServiceFacade;
import cl.ejercicio.data.dtos.User;
import cl.ejercicio.usecases.dosignin.SignUpCase;

import cl.ejercicio.usecases.getusers.GetUsersUseCase;
import cl.ejercicio.usecases.getusers.models.GetUsersResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cl.ejercicio.usecase.getusers.GetUsersStubs.getGetUsersResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GetUsersTest {

  @Mock private SignUpCase signUpCase;

  @Mock private EjercicioRepository ejercicioRepository;

  private EjercicioProtectedController ejercicioProtectedController;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(16);
  }

  @Before
  public void setup() throws IOException {

    final GetUsersUseCase getUsersUseCase = new GetUsersUseCase(ejercicioRepository);

    final EjercicioServiceFacade globalLogicServiceFacade =
        new EjercicioServiceFacade(signUpCase, getUsersUseCase);

    ejercicioProtectedController = new EjercicioProtectedController(globalLogicServiceFacade);

    final List<User> listUser = new ArrayList<>();
    listUser.add(
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
            UserStubs.getUser().isActive()));

    when(ejercicioRepository.findAll()).thenReturn(listUser);
  }

  @Test
  public void itShouldDoGetUsersWhenApiSuccess() throws IOException {
    final GetUsersResponse getUsersResponse = ejercicioProtectedController.getUsers();

    assertNotNull(getGetUsersResponse());
    assertNotNull(getUsersResponse);
    assertEquals(getUsersResponse, getGetUsersResponse());
  }
}

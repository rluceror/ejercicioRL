package cl.ejercicio.usecases.getusers;

import cl.ejercicio.data.EjercicioRepository;
import cl.ejercicio.data.dtos.User;
import cl.ejercicio.usecases.getusers.models.GetUsersResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static cl.ejercicio.utilities.AppConstant.USERS_DOES_NOT_EXIST_EXCEPTION_MESSAGE;

@Service
public class GetUsersUseCase {

  // region fields
  private final Logger logsDoGetUsersUseCase;
  private final EjercicioRepository ejercicioRepository;
  // endregion

  @Autowired
  public GetUsersUseCase(final EjercicioRepository ejercicioRepository) {
    this.logsDoGetUsersUseCase =
        LogManager.getLogger(GetUsersUseCase.class);
    this.ejercicioRepository = ejercicioRepository;
  }

  public GetUsersResponse getUsers() {
    // Do log class
    logsDoGetUsersUseCase.info("Here I Am: Class:DoGetUsersUseCase, Method: findUserByEmail");
    // Do find users
    final List<User> users = ejercicioRepository.findAll();
    // Do exception users doesn't exist
    Objects.requireNonNull(users, USERS_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
    // Do log class
    logsDoGetUsersUseCase.info(
        "Here I Am: Class:DoGetUsersUseCase, Method: findUserByEmail, Action: Success Find Users");
    // return response
    return new GetUsersResponse(ejercicioRepository.findAll());
  }
}

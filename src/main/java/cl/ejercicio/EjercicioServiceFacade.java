package cl.ejercicio;

import cl.ejercicio.usecases.dosignin.models.SignUpRequest;
import cl.ejercicio.usecases.getusers.GetUsersUseCase;
import cl.ejercicio.usecases.getusers.models.GetUsersResponse;
import cl.ejercicio.usecases.dosignin.SignUpCase;
import cl.ejercicio.usecases.dosignin.models.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjercicioServiceFacade implements EjercicioService {

  // region field
  private final SignUpCase signUpCase;
  private final GetUsersUseCase getUsersUseCase;
    // endregion

  @Autowired
  public EjercicioServiceFacade(
      final SignUpCase signUpCase,
      final GetUsersUseCase getUsersUseCase) {
    this.signUpCase = signUpCase;
    this.getUsersUseCase = getUsersUseCase;

  }

  @Override
  public SignUpResponse doSignIn(final SignUpRequest signUpRequest) {
    // Redirect to doSignInUseCase
    return signUpCase.doSignIn(signUpRequest);
  }

  @Override
  public GetUsersResponse getUsers() {
    // Redirect to doGetUsers
    return getUsersUseCase.getUsers();
  }


}

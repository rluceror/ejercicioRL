package cl.ejercicio;

import cl.ejercicio.usecases.dosignin.models.SignUpRequest;
import cl.ejercicio.usecases.dosignin.models.SignUpResponse;
import cl.ejercicio.usecases.getusers.models.GetUsersResponse;


public interface EjercicioService {

  SignUpResponse doSignIn(SignUpRequest signUpRequest);

  GetUsersResponse getUsers();

}

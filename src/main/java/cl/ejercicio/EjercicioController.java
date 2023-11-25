package cl.ejercicio;

import cl.ejercicio.usecases.dosignin.models.SignUpRequest;
import cl.ejercicio.usecases.dosignin.models.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/ejercicio")
public class EjercicioController {

  // region fields
  private final EjercicioServiceFacade ejercicioServiceFacade;
  // endregion

  // region constructor
  @Autowired
  public EjercicioController(final EjercicioServiceFacade ejercicioRLServiceFacade) {
    this.ejercicioServiceFacade = ejercicioRLServiceFacade;
  }
  // endregion

  //
  @RequestMapping(
      value = "/sign-up",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public SignUpResponse doSignIn(@Valid @RequestBody final SignUpRequest signUpRequest) {
    // redirect to ServiceFacade
    return ejercicioServiceFacade.doSignIn(signUpRequest);
  }
}

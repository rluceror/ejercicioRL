package cl.ejercicio;

import cl.ejercicio.usecases.getusers.models.GetUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ejercicio")
public class EjercicioProtectedController {

  // region fields
  private final EjercicioServiceFacade globalLogicServiceFacade;
  // endregion

  // region fields
  @Autowired
  public EjercicioProtectedController(final EjercicioServiceFacade globalLogicServiceFacade) {
    this.globalLogicServiceFacade = globalLogicServiceFacade;
  }
  // endregion

  //
  @RequestMapping(
      value = "/login",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public GetUsersResponse getUsers() {
    return globalLogicServiceFacade.getUsers();
  }
  //

}

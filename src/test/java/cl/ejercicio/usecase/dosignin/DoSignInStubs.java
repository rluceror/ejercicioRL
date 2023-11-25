package cl.ejercicio.usecase.dosignin;

import cl.ejercicio.usecases.dosignin.models.SignUpRequest;
import cl.ejercicio.usecases.dosignin.models.SignUpResponse;
import cl.ejercicio.utilities.formats.LoadStubs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DoSignInStubs {
  public DoSignInStubs() {}

  public static SignUpRequest getDoSignInRequest() throws IOException {
    return new ObjectMapper()
        .readValue(
            LoadStubs.getStubs("sign-in-request.json"), new TypeReference<SignUpRequest>() {});
  }

  public static SignUpResponse getDoSignInResponse() throws IOException {
    return new ObjectMapper()
        .readValue(
            LoadStubs.getStubs("sign-in-response.json"), new TypeReference<SignUpResponse>() {});
  }
}

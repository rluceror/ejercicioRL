package cl.ejercicio.usecase.getusers;

import cl.ejercicio.usecases.getusers.models.GetUsersResponse;
import cl.ejercicio.utilities.formats.LoadStubs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GetUsersStubs {
  public GetUsersStubs() {}

  public static GetUsersResponse getGetUsersResponse() throws IOException {
    return new ObjectMapper()
        .readValue(
            LoadStubs.getStubs("get-users-response.json"),
            new TypeReference<GetUsersResponse>() {});
  }
}

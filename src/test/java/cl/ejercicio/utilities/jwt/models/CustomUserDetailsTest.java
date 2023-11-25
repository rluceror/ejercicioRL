package cl.ejercicio.utilities.jwt.models;

import org.junit.Test;

import java.util.Arrays;

import static cl.ejercicio.utilities.AppConstant.ROL_ADMIN;
import static cl.ejercicio.utilities.AppConstant.ROL_USER;
import static org.junit.Assert.assertEquals;

public class CustomUserDetailsTest {

  @Test
  public void customUserDetailsTest() {
    final CustomUserDetails customUserDetails =
        new CustomUserDetails(
            "usernameExample", "examplePassword", Arrays.asList(ROL_ADMIN, ROL_USER));
    assertEquals(customUserDetails, customUserDetails);
  }
}

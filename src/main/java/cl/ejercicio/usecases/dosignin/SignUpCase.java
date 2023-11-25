package cl.ejercicio.usecases.dosignin;

import cl.ejercicio.data.EjercicioRepository;
import cl.ejercicio.data.dtos.User;
import cl.ejercicio.usecases.dosignin.models.SignUpRequest;
import cl.ejercicio.utilities.jwt.JwtTokenProvider;
import cl.ejercicio.usecases.dosignin.models.SignUpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cl.ejercicio.utilities.AppConstant.ROL_ADMIN;
import static cl.ejercicio.utilities.AppConstant.ROL_USER;
import static cl.ejercicio.utilities.formats.Date.dateNow;

@Service
public class SignUpCase {

  // region fields
  private final Logger logsDoSignUpUseCase;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final EjercicioRepository ejercicioRepository;
  private final PasswordEncoder passwordEncoder;
  // endregion

  @Autowired
  public SignUpCase(
      final AuthenticationManager authenticationManager,
      final JwtTokenProvider jwtTokenProvider,
      final EjercicioRepository ejercicioRepository,
      final PasswordEncoder passwordEncoder) {
    this.logsDoSignUpUseCase = LogManager.getLogger(SignUpCase.class);
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.ejercicioRepository = ejercicioRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public SignUpResponse doSignIn(final SignUpRequest signUpRequest) {
    // EncryptPassword
    final SignUpRequest encryptSignUpRequest =
        new SignUpRequest(
            signUpRequest.getName(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            signUpRequest.getPhones());
    // Do Log Class
    logsDoSignUpUseCase.info(
        "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Message: {}", encryptSignUpRequest);
    // Do find email user
    User user = ejercicioRepository.findByEmail(signUpRequest.getEmail());
    // Do create token user
    final List<String> listRoles = new ArrayList<>();
    listRoles.add(ROL_ADMIN);
    listRoles.add(ROL_USER);
    //
    final String token = jwtTokenProvider.createToken(signUpRequest.getEmail(), listRoles);
    //
    if (user == null) {
      // Do Register User
      user =
          ejercicioRepository.save(
              new User(
                  signUpRequest.getName(),
                  signUpRequest.getEmail(),
                  encryptSignUpRequest.getPassword(),
                  signUpRequest.getPhones(),
                  dateNow(),
                  dateNow(),
                  dateNow(),
                  token,
                  listRoles,
                  true));
    }
    //
    if (user.getToken() != null) {
      // Do Log Action
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: Create User, Message: {}",
          user);
      // Do authenticate user
      final Authentication doAuthentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  user.getEmail(), signUpRequest.getPassword()));
      // Do log authenticate user
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: user authenticate, Message: {}",
          doAuthentication.getAuthorities());
      // Do log create token user
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: create token user");
      // Do create dateNow
      final String dateNow = dateNow();
      // Do update lastLogin data to user
      final int updateUser =
          ejercicioRepository.updateUser(user.getEmail(), token, dateNow, dateNow);
      // Do log update user
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: update user, Message: {}",
          updateUser);
    }
    // Do create response sign in user
    final SignUpResponse signUpResponse =
        new SignUpResponse(
            user.getId(),
            user.getCreated(),
            user.getModified(),
            user.getLastLogin(),
            user.getToken(),
            user.isActive());
    // Do log response sign in user
    logsDoSignUpUseCase.info(
        "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: create response, Message: {}",
            signUpResponse);
    // create token persistence
    // return response
    return signUpResponse;
  }
}

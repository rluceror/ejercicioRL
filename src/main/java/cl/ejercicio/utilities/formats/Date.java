package cl.ejercicio.utilities.formats;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static cl.ejercicio.utilities.AppConstant.DATE_PATTERN;

public final class Date {

  public static String dateNow() {
    return DateTimeFormatter.ofPattern(DATE_PATTERN)
        .withZone(ZoneOffset.UTC)
        .format(LocalDateTime.now());
  }

  public Date() {}
}

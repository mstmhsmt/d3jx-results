package org.springframework.boot.logging.logback;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;

/**
 * Logback {@link CompositeConverter} colors output using the {@link AnsiOutput} class. A
 * single 'color' option can be provided to the converter, or if not specified color will
 * be picked based on the logging level.
 *
 * @author Phillip Webb
 */
public class ColorConverter extends CompositeConverter<ILoggingEvent> {
  private static final Map<String, AnsiElement> elements;

  static {
    Map<String, AnsiElement> 
<<<<<<< commits-hd_100/spring-projects/spring-boot/41424e4529af056a7bcf95c0afc9892a068950bc-aaac78b3a34855cdd183cd3a8208af3fc8fd10ac/A.java
    elements = new HashMap<>()
=======
    ansiElements = new HashMap<String, AnsiElement>()
>>>>>>> commits-hd_100/spring-projects/spring-boot/41424e4529af056a7bcf95c0afc9892a068950bc-aaac78b3a34855cdd183cd3a8208af3fc8fd10ac/B.java
    ;
    ansiElements.put("faint", AnsiStyle.FAINT);
    ansiElements.put("red", AnsiColor.RED);
    ansiElements.put("green", AnsiColor.GREEN);
    ansiElements.put("yellow", AnsiColor.YELLOW);
    ansiElements.put("blue", AnsiColor.BLUE);
    ansiElements.put("magenta", AnsiColor.MAGENTA);
    ansiElements.put("cyan", AnsiColor.CYAN);
    elements = Collections.unmodifiableMap(ansiElements);
  }

  private static final Map<Integer, AnsiElement> levels;

  static {
    Map<Integer, AnsiElement> 
<<<<<<< commits-hd_100/spring-projects/spring-boot/41424e4529af056a7bcf95c0afc9892a068950bc-aaac78b3a34855cdd183cd3a8208af3fc8fd10ac/A.java
    levels = new HashMap<>()
=======
    ansiLevels = new HashMap<Integer, AnsiElement>()
>>>>>>> commits-hd_100/spring-projects/spring-boot/41424e4529af056a7bcf95c0afc9892a068950bc-aaac78b3a34855cdd183cd3a8208af3fc8fd10ac/B.java
    ;
    ansiLevels.put(Level.ERROR_INTEGER, AnsiColor.RED);
    ansiLevels.put(Level.WARN_INTEGER, AnsiColor.YELLOW);
    levels = Collections.unmodifiableMap(ansiLevels);
  }

  @Override protected String transform(ILoggingEvent event, String in) {
    AnsiElement element = elements.get(getFirstOption());
    if (element == null) {
      element = levels.get(event.getLevel().toInteger());
      element = (element == null ? AnsiColor.GREEN : element);
    }
    return toAnsiString(in, element);
  }

  protected String toAnsiString(String in, AnsiElement element) {
    return AnsiOutput.toString(element, in);
  }
}
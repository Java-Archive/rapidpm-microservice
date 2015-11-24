package org.rapidpm.microservice.optionals.header;

import org.rapidpm.ddi.DI;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by svenruppert on 04.11.15.
 */
public class HeaderScreenPrinter {


  public void printOnScreen() {

    final Set<Class<? extends HeaderInfo>> typesAnnotatedWith = DI.getSubTypesOf(HeaderInfo.class);
    if (typesAnnotatedWith.isEmpty()) {
      String txt =
          "   ___            _    _____  __  ___        __  ____                              _        \n" +
              "  / _ \\___ ____  (_)__/ / _ \\/  |/  / ____  /  |/  (_)__________  ______ _____  __(_)______ \n" +
              " / , _/ _ `/ _ \\/ / _  / ___/ /|_/ / /___/ / /|_/ / / __/ __/ _ \\(_-< -_) __/ |/ / / __/ -_)\n" +
              "/_/|_|\\_,_/ .__/_/\\_,_/_/  /_/  /_/       /_/  /_/_/\\__/_/  \\___/___|__/_/  |___/_/\\__/\\__/ \n" +
              "         /_/                                                                                \n";
      System.out.println(txt);
      System.out.println("");
      System.out.println(" this project is hosted at https://github.com/RapidPM/rapidpm-microservice");
      System.out.println(" examples you can find at  https://github.com/RapidPM/rapidpm-microservice-examples");
      System.out.println(" if you have question      mailto:sven.ruppert@gmail.com or Twitter: @SvenRuppert");
      System.out.println("");
    } else {
      typesAnnotatedWith
          .stream()
          .filter(c -> c.isAnnotationPresent(Header.class))
          .sorted((c1, c2) -> {
            int c1Order = Integer.MIN_VALUE;
            int c2Order = Integer.MIN_VALUE;

            if (c1.isAnnotationPresent(Header.class)) {
              c1Order = c1.getAnnotation(Header.class).order();
            }
            if (c2.isAnnotationPresent(Header.class)) {
              c2Order = c2.getAnnotation(Header.class).order();
            }
            return Integer.compare(c1Order, c2Order);
          })
          .map((Function<Class<? extends HeaderInfo>, Optional<HeaderInfo>>) aClass -> {
            try {
              return Optional.of(DI.activateDI(aClass.newInstance()));
            } catch (InstantiationException | IllegalAccessException e) {
              e.printStackTrace();
            }
            return Optional.empty();
          })
          .filter(Optional::isPresent)
          .map(Optional::get)
          .map(HeaderInfo::createHeaderInfo)
          .forEach(System.out::println);
    }
  }
}

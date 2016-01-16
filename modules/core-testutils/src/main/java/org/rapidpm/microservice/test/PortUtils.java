package org.rapidpm.microservice.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

/**
 * Created by Sven Ruppert on 15.01.16.
 */
public class PortUtils {


  public int nextFreePortForTest() {
    int counter = 0;
    while (counter < 1_00) {
      try {
        final int port = 1024 + (new Random().nextInt(65535 - 2048));
        new ServerSocket(port).close();
        return port;
      } catch (IOException ex) {
        counter = counter + 1;
      }
    }
    // if the program gets here, no port in the range was found
    throw new RuntimeException("no free port found");
  }

}

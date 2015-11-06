package org.rapidpm.microservice.modules.optionals.security.ssl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by b.bosch on 05.11.2015.
 */
public class SSLTest {

    private final String url = "https://127.0.0.1:" + Main.DEFAULT_SERVLET_HTTPS_PORT + Main.MYAPP + "/test"; //from Annotation Servlet
    private final String USER_AGENT = "Mozilla/5.0";

    @Before
    public void setUp() throws Exception {
        String path = this.getClass().getResource("/keystore.jks").getPath();
        List<String> argList = new ArrayList<>();
        argList.add("-key");
        argList.add("macrosreply");
        argList.add("-keystore");
        argList.add(path);
        Main.deploy(Optional.of(argList.toArray(new String[0])));
    }

    @After
    public void tearDown() throws Exception {
        Main.stop();

    }

    @Test
    public void testServletGetRequest() throws Exception {
        Field sslContext = Main.class.getDeclaredField("sslContext");
        boolean accessible = sslContext.isAccessible();
        sslContext.setAccessible(true);
        SSLContext sslContext1 = (SSLContext) sslContext.get(null);
        sslContext.setAccessible(accessible);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext1.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println("response = " + response);
        Assert.assertEquals("Hello World CDI Service", response.toString());
        //print result

    }

    @Test
    public void testServletPostRequest() throws Exception {

        Field sslContext = Main.class.getDeclaredField("sslContext");
        boolean accessible = sslContext.isAccessible();
        sslContext.setAccessible(true);
        SSLContext sslContext1 = (SSLContext) sslContext.get(null);
        sslContext.setAccessible(accessible);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext1.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);


        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");




        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        Assert.assertEquals("Hello World CDI Service", response.toString());


    }
}

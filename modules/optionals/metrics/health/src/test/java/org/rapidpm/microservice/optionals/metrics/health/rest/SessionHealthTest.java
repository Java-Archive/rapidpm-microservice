package org.rapidpm.microservice.optionals.metrics.health.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.metrics.health.rest.api.SessionHealthInfo;
import org.rapidpm.microservice.optionals.metrics.health.rest.api.SessionHealthInfoJsonConverter;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by b.bosch on 04.11.2015.
 */
public class SessionHealthTest  {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String url = "http://127.0.0.1:" + Main.DEFAULT_SERVLET_PORT + Main.MYAPP + "/test"; //from Annotation Servlet

    @Before
    public void startUp(){
        DI.clearReflectionModel();
        DI.activatePackages("org.rapidpm");
        DI.activatePackages("junit.org.rapidpm");
        Main.deploy();
    }
    
    @After
    public void tearDown(){
        Main.stop();
        DI.clearReflectionModel();
    }
    
    @Test
    public void heathTest001(){
        final String generateBasicReqURL = generateBasicReqURL(SessionHealth.class);
        Client client = ClientBuilder.newClient();
        System.out.println("generateBasicReqURL = " + generateBasicReqURL);
        final Invocation.Builder authcode = client
                .target(generateBasicReqURL)
                .request();
        final Response response = authcode.get();

        Assert.assertEquals(200, response.getStatus());
        String val = response.getStatusInfo().toString();
        System.out.println("response status info = " + val);
        client.close();
        
    }


    @Test
    public void heathTest002(){
        final String generateBasicReqURL = generateBasicReqURL(SessionHealth.class);
        Client client = ClientBuilder.newClient();
        System.out.println("generateBasicReqURL = " + generateBasicReqURL);
        final Invocation.Builder authcode = client
                .target(generateBasicReqURL)
                .request();
        String response= authcode.get(String.class);
        Gson gson = new Gson();
        SessionHealthInfo[] sessionHealthInfo = gson.fromJson(response,SessionHealthInfo[].class );

        Assert.assertEquals(0L,sessionHealthInfo[0].activeSessionCount);

        client.close();

    }

    @Test
    public void heathTest003() throws IOException {
        generateSession();
        generateSession();
        generateSession();

        String url = generateBasicReqURL(SessionHealth.class);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
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

        final String jsonResponse = response.toString();
        Assert.assertFalse(jsonResponse.isEmpty());

        final List<SessionHealthInfo> healthInfos = new SessionHealthInfoJsonConverter().fromJsonList(jsonResponse);
        Assert.assertEquals(3L,healthInfos.get(0).activeSessionCount);
    }

    public void generateSession() throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
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
    }

    public String generateBasicReqURL(Class restClass) {
        final String restAppPath = Main.CONTEXT_PATH_REST;
        return new RestUtils().generateBasicReqURL(restClass, restAppPath);
    }
}

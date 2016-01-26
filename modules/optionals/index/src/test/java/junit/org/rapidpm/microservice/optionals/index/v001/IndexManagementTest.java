package junit.org.rapidpm.microservice.optionals.index.v001;

import com.google.gson.Gson;
import junit.org.rapidpm.microservice.optionals.index.IndexBasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import java.util.List;

/**
 * Created by Sven Ruppert on 19.01.16.
 */
public class IndexManagementTest extends IndexBasicRestTest {

  public static final String TESTINDEX = IndexManagementTest.class.getSimpleName();

  @Override
  public String getTestIndexName() {
    return TESTINDEX;
  }

  @Test
  public void listAllIndices() throws Exception {
    final String generateBasicReqURL = generateBasicReqURL(IndexManagement.class);
    final Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final Builder clientReq = client
        .target(generateBasicReqURL)
        .path(IndexManagement.LIST_ALL_INDICES)
        .request();
    final String response = clientReq.get(String.class);
    client.close();
    System.out.println("response = " + response);
    final List<String> fromJson = new Gson().fromJson(response, List.class);
    Assert.assertNotNull(fromJson);
    Assert.assertFalse(fromJson.isEmpty());
    Assert.assertEquals(1, fromJson.size());
    Assert.assertEquals(TESTINDEX, fromJson.get(0));
  }


}
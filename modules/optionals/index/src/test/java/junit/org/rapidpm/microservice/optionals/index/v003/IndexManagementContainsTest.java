package junit.org.rapidpm.microservice.optionals.index.v003;

import junit.org.rapidpm.microservice.optionals.index.IndexBasicRestTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.optionals.index.IndexManagement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;

/**
 * Created by Sven Ruppert on 26.01.16.
 */
public class IndexManagementContainsTest extends IndexBasicRestTest {

  public static final String TESTINDEX = IndexManagementContainsTest.class.getSimpleName();

  @Test
  public void test001() throws Exception {
    final String generateBasicReqURL = generateBasicReqURL(IndexManagement.class);
    final Client client = ClientBuilder.newClient();
    System.out.println("generateBasicReqURL = " + generateBasicReqURL);
    final Builder clientReq = client
        .target(generateBasicReqURL)
        .path(IndexManagement.CONTAINS_INDEX)
        .queryParam(IndexManagement.CONTAINS_INDEX_QUERYPARAM, TESTINDEX)
        .request();
    final Boolean response = clientReq.get(Boolean.class);
    client.close();
    Assert.assertTrue(response);
  }

  @Override
  public String getTestIndexName() {
    return TESTINDEX;
  }
}

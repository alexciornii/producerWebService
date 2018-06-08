package md.liberopay.producer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.support.MockStrategiesHelper;
import org.springframework.ws.transport.WebServiceMessageReceiver;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EndpointMockTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ClientInterceptor testInterceptor;

    private MockWebServiceClient mockClient;

    @Before
    public void setUp() {
        MockStrategiesHelper strategiesHelper = new MockStrategiesHelper(applicationContext);

        ClientInterceptor[] interceptors = new ClientInterceptor[]{testInterceptor};
        WebServiceMessageReceiver messageReceiver =
                strategiesHelper.getStrategy(WebServiceMessageReceiver.class, SoapMessageDispatcher.class);
        WebServiceMessageFactory messageFactory =
                strategiesHelper.getStrategy(WebServiceMessageFactory.class, SaajSoapMessageFactory.class);
        mockClient = MockWebServiceClient.createClient(new InterceptingMessageReceiver(messageReceiver, interceptors), messageFactory);
    }

    @Test
    public void customerEndpoint() {

        Source requestPayload = new StringSource(
                "<ns2:UserDetailsRequest xmlns:ns2=\"http://www.liberopay.md/producer\">"
                        + "<ns2:id>1</ns2:id>"
                        + "</ns2:UserDetailsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:UserDetailsResponse xmlns:ns2=\"http://www.liberopay.md/producer\">"+
                            "<ns2:User>"+
                                "<ns2:id>1</ns2:id>"+
                                "<ns2:firstName>Vasile</ns2:firstName>" +
                                "<ns2:lastName>Osoianu</ns2:lastName>" +
                                "<ns2:age>27</ns2:age>" +
                                "<ns2:address>mun.Chisinau</ns2:address>" +
                            "</ns2:User>" +
                        "</ns2:UserDetailsResponse>");

        mockClient.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }
}

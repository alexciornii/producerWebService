package md.liberopay.producer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EndpointTest {

    @Rule
    public OutputCapture output = new OutputCapture();

    @Autowired
    private WebServiceTemplate webServiceTemplate;


    @LocalServerPort
    private int port;

    @Before
    public void init(){
        webServiceTemplate.setDefaultUri(format("http://localhost:%s/service/users.wsdl", port));
    }

    @Test
    public void getUserTest() {

        UserDetailsRequest request = new UserDetailsRequest();
        request.setId(1L);

        UserDetailsResponse response = (UserDetailsResponse)
                webServiceTemplate.marshalSendAndReceive(request);

        System.out.println("Got Response As below ========= : ");
        System.out.println("First name : "+response.getUser().getFirstName());
        System.out.println("Last name : "+response.getUser().getLastName());
        System.out.println("Address : "+response.getUser().getAddress());
        System.out.println("Age : "+response.getUser().getAge());

        assertThat(this.output.toString()).contains("mun.Chisinau");
    }
}
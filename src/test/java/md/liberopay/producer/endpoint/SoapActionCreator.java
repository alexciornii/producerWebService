package md.liberopay.producer.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.support.creator.PayloadMessageCreator;

import javax.xml.transform.Source;
import java.io.IOException;

@AllArgsConstructor
public class SoapActionCreator implements RequestCreator {

    private final Source payload;

    private final String soapAction;

    @Override
    public WebServiceMessage createRequest(WebServiceMessageFactory webServiceMessageFactory)
            throws IOException {
        WebServiceMessage webServiceMessage =
                new PayloadMessageCreator(payload).createMessage(webServiceMessageFactory);

        SoapMessage soapMessage = (SoapMessage) webServiceMessage;
        soapMessage.setSoapAction(soapAction);

        return webServiceMessage;
    }
}

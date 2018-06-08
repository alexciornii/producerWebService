package md.liberopay.producer;

import net.javacrumbs.smock.common.InterceptingTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.transport.WebServiceMessageReceiver;
import org.springframework.ws.context.MessageContext;

public class InterceptingMessageReceiver implements WebServiceMessageReceiver {

    private final WebServiceMessageReceiver wrappedMessageReceiver;

    private final InterceptingTemplate interceptingTemplate;

    public InterceptingMessageReceiver(WebServiceMessageReceiver wrappedMessageReceiver,
                                       ClientInterceptor[] interceptors) {
        this.wrappedMessageReceiver = wrappedMessageReceiver;
        this.interceptingTemplate = new InterceptingTemplate(interceptors);
    }

    public void receive(MessageContext messageContext) throws Exception {
        interceptingTemplate.interceptRequest(messageContext, wrappedMessageReceiver);
    }
}

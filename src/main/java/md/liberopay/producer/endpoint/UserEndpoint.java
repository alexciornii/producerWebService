package md.liberopay.producer.endpoint;

import lombok.RequiredArgsConstructor;
import md.liberopay.producer.UserDetailsRequest;
import md.liberopay.producer.UserDetailsResponse;
import md.liberopay.producer.repository.UserRepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {
	private static final String NAMESPACE_URI = "http://www.liberopay.md/producer";

	private final UserRepository userRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserDetailsRequest")
	@ResponsePayload
	public UserDetailsResponse getUser(@RequestPayload UserDetailsRequest request) {
		UserDetailsResponse response = new UserDetailsResponse();
		response.setUser(userRepository.findUserById(request.getId()));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserDetailsRequest")
	@ResponsePayload
	public UserDetailsResponse getUsers(@RequestPayload UserDetailsRequest request) {
		UserDetailsResponse response = new UserDetailsResponse();
		response.setUsers(userRepository.getUsers());

		return response;
	}
}
package com.github.tbertell.openchannel.authentication;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;

public class AuthenticationHandler implements RequestHandler {

	private static final Map<String, String> USERS = new HashMap<String, String>();

	static {
		USERS.put("test", "test1");
	}

	public Response handleRequest(Message message, ClassResourceInfo resourceClass) {
		AuthorizationPolicy policy = (AuthorizationPolicy) message.get(AuthorizationPolicy.class);

		if (policy != null && isAuthenticated(policy.getUserName(), policy.getPassword())) {
			// let request to continue
			return null;
		} else {
			// authentication failed, request the authetication, add the realm
			// name if needed to the value of WWW-Authenticate
			// return Response.status(401).header("WWW-Authenticate",
			// "Basic").build();
			System.out.println("palautetaan 403");
			return Response.status(403).build();
		}
	}

	private boolean isAuthenticated(String username, String password) {
		String pwd = USERS.get(username);
		if (pwd != null && pwd.equals(password)) {
			return true;
		} else {
			return false;
		}
	}
}

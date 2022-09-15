package com.vecondev.bo.authorizer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayCustomAuthorizerEvent;
import com.amazonaws.services.lambda.runtime.events.IamPolicyResponse;
import com.amazonaws.services.lambda.runtime.events.IamPolicyResponse.PolicyDocument;
import com.amazonaws.services.lambda.runtime.events.IamPolicyResponse.Statement;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authorizer
    implements RequestHandler<APIGatewayCustomAuthorizerEvent, IamPolicyResponse> {

  public static final String USER_ID = "id";
  public static final String USER_AUTHORITIES = "authorities";
  public static final String USER_USERNAME = "username";
  public static final String EXECUTION_POLICY = "execute-api";
  public static final String ALLOW_INVOCATION = ":Invoke";
  public static final String VERSION = "2012-10-17";
  public static final String AUTHENTICATION_SUCCESS_RESULT = "Allow";
  public static final String AUTHENTICATION_FAIL_RESULT = "Deny";

  public IamPolicyResponse handleRequest(APIGatewayCustomAuthorizerEvent event, Context context) {
    final LambdaLogger logger = context.getLogger();
    Map<String, Object> params = new HashMap<>();
    String token = event.getAuthorizationToken();
    String auth = AUTHENTICATION_FAIL_RESULT;
    if (token != null) {
      token = token.replace(JwtConfigProperties.getAuthorizationHeaderPrefix(), "");
    } else {
      logger.log("The access token is missing");
      return null;
    }

    try {
      JWTVerifier jwtVerifier =
          JWT.require(Algorithm.RSA256((RSAPublicKey) CertificateManager.publicKey())).build();
      DecodedJWT decodedJwt = jwtVerifier.verify(token);
      params.put(USER_ID, decodedJwt.getClaim(USER_ID).asString());
      params.put(USER_USERNAME, decodedJwt.getSubject());
      params.put(USER_AUTHORITIES, decodedJwt.getClaim(USER_AUTHORITIES).toString());
      auth = AUTHENTICATION_SUCCESS_RESULT;
      logger.log("Successful authorization.");
    } catch (TokenExpiredException ex) {
      logger.log("The access token is expired");
    } catch (JWTVerificationException ex) {
      logger.log("The access token is invalid");
    }

    return getResponse(event, auth, params);
  }

  public IamPolicyResponse getResponse(
      APIGatewayCustomAuthorizerEvent event, String auth, Map<String, Object> params) {
    Statement statement =
        Statement.builder()
            .withAction(EXECUTION_POLICY + ALLOW_INVOCATION)
            .withEffect(auth)
            .withResource(List.of(event.getMethodArn()))
            .build();
    return IamPolicyResponse.builder()
        .withPrincipalId((String) params.get(USER_ID))
        .withPolicyDocument(new PolicyDocument(VERSION, List.of(statement)))
        .withContext(params)
        .build();
  }
}
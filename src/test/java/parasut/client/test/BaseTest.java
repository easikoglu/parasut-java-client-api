/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package parasut.client.test;


import http.HttpOptions;
import lombok.extern.slf4j.Slf4j;
import model.auth.ParasutAuthPasswordRequest;
import model.auth.ParasutAuthRefreshRequest;
import model.auth.ParasutGetToken;
import model.auth.enums.ParasutGrantType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
public class BaseTest {


    static HttpOptions httpOptions;


    static String redirectUri;
    static String username;
    static String password;
    static String companyId;


    ParasutGetToken parasutGetToken;


    @BeforeClass
    public static void beforeClass() {
        InputStream is = ClassLoader.getSystemResourceAsStream("test.config.properties");
        try {
            Properties props = new Properties();

            props.load(is);
            httpOptions = new HttpOptions();
            httpOptions.setApiKey(props.getProperty("clientId"));
            httpOptions.setSecretKey(props.getProperty("clientSecret"));
            httpOptions.setBaseUrl(props.getProperty("baseUrl"));
            httpOptions.setApiVersion(props.getProperty("apiVersion"));
            redirectUri = (String) props.get("redirectUri");
            username = (String) props.get("username");
            password = (String) props.get("password");
            companyId = (String) props.get("companyId");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Before
    public void before() {

        parasutGetToken = getToken();
    }

    public ParasutGetToken getToken() {
        return ParasutGetToken.get(httpOptions, ParasutAuthPasswordRequest.builder()
                .password(password)
                .username(username)
                .clientId(httpOptions.getApiKey())
                .clientSecret(httpOptions.getSecretKey())
                .redirectUri(redirectUri)
                .grant_type(ParasutGrantType.PASSWORD.getValue()).build());

    }

    /**
     * get token with grant_type=password
     */
    @Test
    public void getAuthToken_withPasswordGrantType() {
        log.info(parasutGetToken.toString());
        assertThat(parasutGetToken.getTokenType(), equalTo("bearer"));

    }

    /**
     * get token with refresh token
     */
    @Test
    public void getToken_withRefreshTokenRequest() {
        ParasutGetToken parasutRefreshedToken = ParasutGetToken
                .refresh(httpOptions, ParasutAuthRefreshRequest.builder()
                        .refresh_token(parasutGetToken.getRefreshToken())
                        .clientId(httpOptions.getApiKey())
                        .clientSecret(httpOptions.getSecretKey())
                        .redirectUri(redirectUri)
                        .grant_type(ParasutGrantType.REFRESH_TOKEN.getValue()).build());

        assertThat(parasutRefreshedToken.getTokenType(), equalTo("bearer"));
    }


}

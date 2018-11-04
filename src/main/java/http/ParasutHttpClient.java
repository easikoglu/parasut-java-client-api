package http;

import com.google.gson.Gson;
import exception.ParasutHttpClientException;
import http.enums.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.CodingErrorAction;
import java.util.Map;
import java.util.Objects;

import static http.enums.HttpMethod.GET;
import static http.enums.HttpMethod.POST;

/**
 * @author erhanasikoglu
 */
@Slf4j
public class ParasutHttpClient {

    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final int TIMEOUT = 140000;
    private static final String USER_AGENT = "User-Agent";
    private static final String USER_AGENT_PROPERTY = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0";

    Map<String, String> headers;
    Object request;
    String url;
    CloseableHttpClient httpClient;
    HttpPost httpPost;
    HttpGet httpGet;
    RequestConfig requestConfig;
    private CloseableHttpResponse closeableHttpResponse;
    private BufferedReader bufferedReader;


    public ParasutHttpClient() {
        // SSL context for secure connections can be created either based on
        // system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();

        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();

        // Create a connection manager with custom configuration.
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);

        // Create socket configuration
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();
        // Configure the connection manager to use socket configuration either
        // by default or for a specific host.
        connManager.setDefaultSocketConfig(socketConfig);
        // Validate connections after 2 sec of inactivity
        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();


        connManager.setDefaultConnectionConfig(connectionConfig);

        this.httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();


        // Request configuration can be overridden at the request level.
        this.requestConfig = RequestConfig.custom()
                .setSocketTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .build();
    }

    public <T> T get(Class<T> responseType) {
        try {
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            Gson gson = new Gson();

            String response = send(GET, null, httpGet);
            return gson.fromJson(response, responseType);
        } catch (IOException e) {
            throw new ParasutHttpClientException(e.getMessage(), e);
        }
    }

    public <T> T post(Class<T> responseType) {
        httpPost = new HttpPost();
        httpPost.setURI(URI.create(url));

        try {
            Gson gson = new Gson();
            String body = gson.toJson(request);

            StringEntity entity = new StringEntity(body);
            httpPost.setEntity(entity);
            String response = send(POST, entity, httpPost);
            return gson.fromJson(response, responseType);

        } catch (IOException e) {
            throw new ParasutHttpClientException(e.getMessage(), e);
        }
    }

    private String send(HttpMethod httpMethod, StringEntity entity, HttpRequestBase requestBase) throws IOException {

        if (Objects.nonNull(entity) && HttpMethod.POST.equals(httpMethod)) {
            httpPost.setEntity(entity);
        }
        prepareHeaders(requestBase);
        return getResponse(requestBase);
    }

    private void prepareHeaders(HttpRequestBase requestBase) {

        requestBase.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        requestBase.setHeader(ACCEPT, APPLICATION_JSON);
        requestBase.setHeader(USER_AGENT, USER_AGENT_PROPERTY);


        if (Objects.nonNull(headers)) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                requestBase.setHeader(header.getKey(), header.getValue());
            }
        }
    }

    private String getResponse(HttpRequestBase requestBase) {
        try {
            closeableHttpResponse = httpClient.execute(requestBase);

            return getContent();
        } catch (IOException e) {
            throw new ParasutHttpClientException(e.getMessage(), e);
        } finally {
            try {
                EntityUtils.consume(closeableHttpResponse.getEntity());
                after(requestBase);
            } catch (IOException e) {
                throw new ParasutHttpClientException(e.getMessage(), e);
            }
        }
    }

    private String getContent() throws IOException {


        bufferedReader = new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity()
                .getContent()));
        String body = "";
        StringBuilder content = new StringBuilder();
        while ((body = bufferedReader.readLine()) != null) {
            content.append(body).append("\n");
        }

        return content.toString().trim();
    }

    public final void after(HttpRequestBase requestBase) throws IllegalStateException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("closing http client connection...");
        }
        requestBase.completed();
        try {
            httpClient.close();

        } catch (final IOException e1) {

            throw e1;
        }
        try {
            if (Objects.nonNull(bufferedReader))
                bufferedReader.close();
        } catch (final IOException e) {

            throw e;
        }
        closeResponse(closeableHttpResponse);
    }

    public static void closeResponse(CloseableHttpResponse response) throws IOException {

        if (response == null) {
            return;
        }

        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity.getContent().close();
            }
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("closed http client connection...");
            }
            response.close();

        }
    }


    public static ClientBuilder builder() {
        return new ClientBuilder();
    }


    /***
     * Builder pattern applied here to make easy to populate Client object.
     */
    public static class ClientBuilder {

        private Map<String, String> headers;
        private Object request;
        private String url;

        public ClientBuilder() {
        }

        public ClientBuilder addUrl(String url) {
            this.url = url;
            return this;
        }

        public ClientBuilder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public ClientBuilder withData(Object request) {
            this.request = request;
            return this;
        }

        public ParasutHttpClient build() {

            ParasutHttpClient client = new ParasutHttpClient();
            client.headers = this.headers;
            client.url = this.url;
            client.request = this.request;

            return client;
        }
    }


}

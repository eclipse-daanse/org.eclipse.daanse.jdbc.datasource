/*
* Copyright (c) 2024 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.jdbc.datasource.opensearch.integration;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.Hashtable;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.opensearch.api.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.service.cm.Configuration;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.InjectConfiguration;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
class OSDataSourceTest {

    private static final String USER = "admin";
    private static final String PW = "fdf2FW?WW93?!";
    private static final String LOCALHOST = "localhost";
    private static final int PORT_9200 = 9200;
    private static final String TABLE = "mytableee";

    @Test
    void noConfigurationNoServiceTest(
            @InjectService(cardinality = 0, timeout = 500) ServiceAware<DataSource> saDataSource, //
            @InjectService(cardinality = 0, timeout = 500) ServiceAware<XADataSource> saXaDataSource, //
            @InjectService(cardinality = 0, timeout = 500) ServiceAware<ConnectionPoolDataSource> saCpDataSource)
            throws Exception {

        assertThat(saDataSource.getServices()).isEmpty();
        assertThat(saXaDataSource.getServices()).isEmpty();
        assertThat(saCpDataSource.getServices()).isEmpty();

    }

    @Test
    @WithFactoryConfiguration(factoryPid = org.eclipse.daanse.jdbc.datasource.opensearch.api.Constants.PID_DATASOURCE, name = "1", location = "?", properties = @Property(key = Constants.DATASOURCE_PROPERTY_HOST, value = LOCALHOST))
    void serviceWithConfigurationTest(@InjectService(timeout = 5000) ServiceAware<DataSource> serviceAwareDataSource) //
            throws Exception {

        assertThat(serviceAwareDataSource.getServices()).hasSize(1);

    }

    @Test
    void test(
            @InjectConfiguration(withFactoryConfig = @WithFactoryConfiguration(factoryPid = org.eclipse.daanse.jdbc.datasource.opensearch.api.Constants.PID_DATASOURCE, name = "1", location = "?")) Configuration configuration,
            @InjectService(cardinality = 0) ServiceAware<DataSource> saDataSource) //
            throws Exception {

        try (GenericContainer container = new GenericContainer<>("opensearchproject/opensearch:latest")) {

            container.withEnv("OPENSEARCH_INITIAL_ADMIN_PASSWORD", PW)//
                    .withEnv("discovery.type", "single-node")//
                    .withExposedPorts(443, PORT_9200, 9600);

            WaitStrategy waitStrategy = new HttpWaitStrategy().usingTls().allowInsecure().forPort(PORT_9200)
                    .withBasicCredentials(USER, PW)
                    .forStatusCodeMatching(response -> response == HTTP_OK || response == HTTP_UNAUTHORIZED)
                    .withReadTimeout(Duration.ofSeconds(30)).withStartupTimeout(Duration.ofMinutes(5));
            container.setWaitStrategy(waitStrategy);

            container.start();

            Thread.sleep(1000);

            int port443 = container.getMappedPort(443);
            System.out.println("443=" + port443);
            int port9200 = container.getMappedPort(9200);
            System.out.println("9200=" + port9200);
            int port9600 = container.getMappedPort(9600);
            System.out.println("9600=" + port9600);
            bulkImport(port9200);
//            Thread.sleep(100000000);
            Hashtable<String, Object> ht = new Hashtable<>();
            ht.put(Constants.DATASOURCE_PROPERTY_HOST, LOCALHOST);
            ht.put(Constants.DATASOURCE_PROPERTY_PORT, port9200);
            ht.put(Constants.DATASOURCE_PROPERTY_USERNAME, USER);
            ht.put(Constants.DATASOURCE_PROPERTY_PASSWORD, PW);
//            ht.put(Constants.DATASOURCE_PROPERTY_AUTH, "BASIC");
            ht.put(Constants.DATASOURCE_PROPERTY_TRUSTSELFSIGNED, true);
            ht.put(Constants.DATASOURCE_PROPERTY_USESSL, true);
            configuration.update(ht);

            Thread.sleep(1000);
            Connection connection = saDataSource.waitForService(1000).getConnection();
            java.sql.Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE);

            boolean b = false;
            while (rs.next()) {
                String s = rs.getString(1);
                System.out.println(s);
                b = true;
            }

            assertThat(b).isTrue();

            container.stop();
        }
    }

    void bulkImport(int port) throws IOException, InterruptedException, URISyntaxException, KeyManagementException,
            NoSuchAlgorithmException {

        // Set up the SSL context to trust self-signed certificates
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new NoopTrustManager() }, null);

        // Step 2: Set up Basic Authentication
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Provide the username and password for Basic Authentication
                return new PasswordAuthentication("admin", "fdf2FW?WW93?!".toCharArray());
            }
        };

        // Create the HttpClient with SSLContext and basic authentication
        HttpClient client = HttpClient.newBuilder().sslContext(sslContext).authenticator(authenticator).build();

        // URL of the OpenSearch Server
        String createIndexUrl = "http://" + LOCALHOST + ":" + port + "/" + TABLE;

        // JSON to create the index (optional, you can add mappings here if needed)
        String createIndexData = """
        {
            "mappings":{
                "properties":{
                    "member":{
                        "type":"keyword"
                    },
                    "measure":{
                        "type":"float"
                    }
                }
            }
        }
        """;
        // Request to create the index
        HttpRequest createIndexRequest = HttpRequest.newBuilder().uri(new URI(createIndexUrl))
                .header("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(createIndexData))
                .build();

        try {

            // Send the request to create the index
            HttpResponse<String> createIndexResponse = client.send(createIndexRequest,
                    HttpResponse.BodyHandlers.ofString());

            // Print the response code and body (this will show if index was created successfully)
            System.out.println("Create Index Response Code: " + createIndexResponse.statusCode());
            System.out.println("Create Index Response Body: " + createIndexResponse.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {

            // URL to insert a document into the "owner" index
            String insertDocUrl = "https://localhost:" + port + "/" + TABLE + "/_doc/" + i; // Document ID is i
            String jsonData = "{ \"member\": \"" + member(i) + "\", \"measure\": " + i + " }";
            // Request to insert the document
            HttpRequest insertDocRequest = HttpRequest.newBuilder().uri(new URI(insertDocUrl))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();

            // Send the request to insert the document
            HttpResponse<String> insertDocResponse = client.send(insertDocRequest,
                    HttpResponse.BodyHandlers.ofString());

            // Print the response code and body (this will show if the document was inserted successfully)
            System.out.println("Insert Document Response Code: " + insertDocResponse.statusCode());
            System.out.println("Insert Document Response Body: " + insertDocResponse.body());
        }

    }

    private String member(int i) {
        return i > 5 ? "B" : "A";
    }

    // A TrustManager that trusts all certificates (useful for self-signed certificates)
    private static class NoopTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    }
}

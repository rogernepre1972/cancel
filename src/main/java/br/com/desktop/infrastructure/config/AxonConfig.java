package br.com.desktop.infrastructure.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.messaging.correlation.CorrelationDataProvider;
import org.axonframework.messaging.correlation.MessageOriginProvider;
import org.axonframework.messaging.correlation.SimpleCorrelationDataProvider;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AxonConfig {
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private int mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;

    @Value("${spring.data.mongodb.username}")
    private String mongoUsername;

    @Value("${spring.data.mongodb.password}")
    private String mongoPassword;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder().mongoTemplate(axonMongoTemplate()).serializer(serializer).build();
    }

//    @Bean
//    public EventStorageEngine eventStorageEngine(MongoClient client) {
//        return MongoEventStorageEngine.builder().mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build()).build();
//    }

    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder().mongoDatabase(mongo(), mongoDatabase).build();
    }

    @Bean
    public MongoClient mongo() {
        try {
//            MongoClient client = MongoClients.create()
            MongoClientSettings.Builder settings = MongoClientSettings.builder();
            //TODO: Revove after config final database with authentication
            if(activeProfile.equals("local")) {

                settings.credential(
                        MongoCredential
                                .createScramSha1Credential(mongoUsername, mongoDatabase, mongoPassword.toCharArray()));
            }
            settings.applyToClusterSettings(builder ->
                            builder.hosts(List.of(
                                    new ServerAddress(mongoHost, mongoPort))))
                    .build();
            return MongoClients.create(settings.build());
        } catch (Exception e) {
            System.out.println("Deu ruim");
            e.printStackTrace();
        }
        return null;
    }

    // The `MongoEventStorageEngine` stores each event in a separate MongoDB document
    @Bean
    public EventStorageEngine storageEngine() {
        return MongoEventStorageEngine.builder()
                .eventSerializer(
                        XStreamSerializer.builder()
                                .xStream(SecureXStreamSerializer.xStream())
                                .build()

                )
                .snapshotSerializer(
                        XStreamSerializer.builder()
                                .xStream(SecureXStreamSerializer.xStream())
                                .build()
                )
                .mongoTemplate(axonMongoTemplate()).build();
    }

    // The Event store `EmbeddedEventStore` delegates actual storage and retrieval of events to an `EventStorageEngine`.
    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

    // Configuring a single CorrelationDataProvider will automatically override the default MessageOriginProvider
    @Bean
    public CorrelationDataProvider someKeyCorrelationProvider() {
        return new SimpleCorrelationDataProvider("someKey");
    }

    @Bean
    public CorrelationDataProvider messageOriginProvider() {
        return new MessageOriginProvider();
    }
}
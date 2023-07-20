package com.example.webflux.pageable.entity.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.lang.NonNull;

import java.io.IOException;

@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Bean(destroyMethod="stop")
    public MongodExecutable mongodExecutable() throws IOException {
        String ip = "localhost";
        int port = 27017;

        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.V4_0_12)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();

        return mongodExecutable;
    }

    @Override
    @NonNull
    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return "test_poc";
    }
}

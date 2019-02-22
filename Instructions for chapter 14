# Logging and troubleshooting

## Steps to setup ELK Stake and Zipkin

1. Pull elastic search image
    ```
    docker pull docker.elastic.co/elasticsearch/elasticsearch:5.5.1
    ```
2. Pull logstash image
    ```
    docker pull docker.elastic.co/logstash/logstash:5.5.1
    ```
3. Pull kibana image
    ```
    docker pull docker.elastic.co/kibana/kibana:5.5.1
    ```
4. Pull zipkin image. I prefer its jar. So, downloaded jar.
    ```
    docker pull openzipkin/zipkin
    ```
    or if running jar:
    ```
    wget -O zipkin.jar 'https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec'
    ```
5. Add Sleuth and Zikpin dependencies in your microservice:
    ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-sleuth</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-sleuth-zipkin</artifactId>
    </dependency>
    ```
6. Add logback/logstash dependencies in your microservice:
    ```
    <dependency>
        <groupId>net.logstash.logback</groupId>
        <artifactId>logstash-logback-encoder</artifactId>
        <version>4.6</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-core</artifactId>
        <version>1.1.9</version>
    </dependency>
    ```
7. Add logback.xml to src/main/resources:
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration debug="true">
        <appender name="stash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
            <destination>192.168.99.100:5001</destination>
            <!-- encoder is required -->
            <encoder class="net.logstash.logback.encoder.LogstashEncoder" />
            <keepAliveDuration>5 minutes</keepAliveDuration>
        </appender>
        <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>%d{HH:mm:ss.SSS} [%thread, %X{X-B3-TraceId:-},%X{X-B3-SpanId:-}] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>

        <property name="spring.application.name" value="nameOfService" scope="context"/>

        <root level="INFO">
            <appender-ref ref="stash" />
            <appender-ref ref="stdout" />
        </root>

        <shutdownHook class="ch.qos.logback.core.hook.DelayingShutdownHook"/>
    </configuration>
    ```
6. Docker compose file:
    ```
    version: '2'

    services:
      elasticsearch:
        image: docker.elastic.co/elasticsearch/elasticsearch:5.5.1
        ports:
          - "9200:9200"
          - "9300:9300"
        environment:
          ES_JAVA_OPTS: "-Xmx256m -Xms256m"
          xpack.security.enabled: "false"
          xpack.monitoring.enabled: "false"
          # below is required for running in dev mode. For prod mode remove them and vm_max_map_count kernel setting needs to be set to at least 262144
          http.host: "0.0.0.0"
          transport.host: "127.0.0.1"
        networks:
          - elk

      logstash:
        image: docker.elastic.co/logstash/logstash:5.5.1
        #volumes:
        #  - ~/pipeline:/usr/share/logstash/pipeline
        #  windows manually copy to docker cp pipleline/logstash.conf 305321857e9f:/usr/share/logstash/pipeline. restart container after that
        ports:
          - "5001:5001"
        environment:
          LS_JAVA_OPTS: "-Xmx256m -Xms256m"
          xpack.monitoring.enabled: "false"
          xpack.monitoring.elasticsearch.url: "http://192.168.99.100:9200"
          command: logstash -e 'input { tcp { port => 5001 codec => "json" } } output { elasticsearch { hosts => "192.168.99.100" index => "mmj" } }'
        networks:
          - elk
        depends_on:
          - elasticsearch

      kibana:
        image: docker.elastic.co/kibana/kibana:5.5.1
        ports:
          - "5601:5601"
        environment:
          xpack.security.enabled: "false"
          xpack.reporting.enabled: "false"
          xpack.monitoring.enabled: "false"
        networks:
          - elk
        depends_on:
          - elasticsearch

    networks:
      elk:
        driver: bridge
    ```
6. Run elk stack, zipkin and all microservices after re-build
    ```
    docker-compose -f docker-compose-elk.yml up -d
    ```
    If volume is not used and environment pipeline does not work. Then, you can copy the pipleline conf inside container and restart logstash container.
    ```
    docker cp pipleline/logstash.conf <logstash container id>:/usr/share/logstash/pipeline
    ```
    pipeline/logstash.conf:
    ```
    input {
      tcp {
        port => 5001
        codec => "json"
      }
    }
    
    output {
      elasticsearch {
        hosts => "elasticsearch:9200"
      }
    }
    ```
    ```
    java -jar <path to microservice jar>
    ```
    ```
    java -jar zipkin.jar
    ```
7. Wait for few mins and then access following URLs: (Replace the IP based on your conf)
   To check if elasticsearch is up:
   ```
   http://192.168.99.100:9200/
   ```
   To check if indexes are created or not:
   ```
   http://192.168.99.100:9200/_cat/indices?v
   or
   http://192.168.99.100:9200/_aliases?pretty
   ```
   Once logstash index is done (you may have to his few service endpoints to generate some logs). Access the Kibana:
   ```
   http://192.168.99.100:5601/
   ```
   Create index and then graph to view different logs. Make use of the traceId generated by sleuth.
8. Access the Zipkin Dashboard and find out the time taken by difference requests.
   ```
   http://localhost:9411/zipkin/
   ```

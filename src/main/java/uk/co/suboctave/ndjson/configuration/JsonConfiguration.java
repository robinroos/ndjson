package uk.co.suboctave.ndjson.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;

/**
 * If this Configuration class is not present, or its content is commented out, then the application fails with:
 * No Encoder for [uk.co.suboctave.ndjson.dto.Record] with preset Content-Type 'null'
 * Due to the lack of an ObjectMapper for MimeType "application/x-ndjson".
 *
 * If this Configuration class is present, then the application returns invalid "application/x-ndjson" content
 * (the returned stream of records are reflected as an array with [ ... ] tokens and comma-separators).
 *
 * e.g.
 *
 * curl -X GET "http://localhost:8080/record/all" -H  "accept: application/x-ndjson"
 *
 * [{"name":"Record-0"},{"name":"Record-1"},{"name":"Record-2"},{"name":"Record-3"},{"name":"Record-4"}]
 *
 * I chatted with the jackson-databing guys on Gitter and they say it is not possible to force an ObjectMapper into
 * emitting NDJSON format from the ObjectMapper itself; that can be accomplished only where write()-like methods are being invoked.
 *
 * Whilst I can configure the ObjectMapper before I register it for "application/x-ndjson", I am not invoking write()-like methods on it;
 * that is done higher up the application stack by Spring WebFlux.
 */
@Configuration
public class JsonConfiguration {

    @Bean
    public CodecCustomizer ndJsonCustomizer(ObjectMapper objectMapper) {
        Jackson2JsonDecoder jsonDecoder = new Jackson2JsonDecoder(
                objectMapper,
                new MimeType("application", "json"),
                new MimeType("application", "x-ndjson"));
        Jackson2JsonEncoder jsonEncoder = new Jackson2JsonEncoder(
                objectMapper,
                new MimeType("application", "json"),
                new MimeType("application", "x-ndjson"));
        return codecs -> {
            codecs.defaultCodecs().jackson2JsonDecoder(jsonDecoder);
            codecs.defaultCodecs().jackson2JsonEncoder(jsonEncoder);
        };

    }

}

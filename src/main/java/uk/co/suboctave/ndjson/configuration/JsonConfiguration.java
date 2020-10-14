package net.uk.suboctave.ndjson.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;

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

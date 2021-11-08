package ru.bank.validation.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    @Bean
    public XStream xStream() {
        var xStream = new XStream();
        xStream.allowTypesByWildcard(
            new String[]{"ru.bank.common.events.**", "ru.bank.common.commands.**",
                "ru.bank.application.queries.**"});

        return xStream;
    }

    @Bean
    @Primary
    public Serializer serializer(XStream xStream) {
        return XStreamSerializer.builder().xStream(xStream).build();
    }
}

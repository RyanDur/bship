package com.bship.games.Configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.bship.games.domains.Harbor.DESTROYER;
import static com.bship.games.domains.Harbor.INVALID_SHIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HarborDeserializerTest {
    private JsonFactory factory;
    private HarborDeserializer deserializer;
    private ObjectMapper mapper;
    private DeserializationContext context;
    private JsonFactory jsonFactory;

    @Before
    public void setup() {
        factory = new JsonFactory();
        deserializer = new HarborDeserializer();
        mapper = new ObjectMapper();
        context = mock(DeserializationContext.class);
        jsonFactory = factory.setCodec(mapper);
    }


    @Test
    public void shouldThrowJsonMappingExceptionWhenAnEmptyValue() throws IOException {
        JsonParser parser = jsonFactory.createParser("");

        String message = "Cannot deserialize empty value";
        when(context.mappingException(message))
                .thenReturn(JsonMappingException.from(parser, message));

        assertThatThrownBy(() -> deserializer.deserialize(parser, context))
                .hasMessageStartingWith(message);
        verify(context).mappingException(message);
    }

    @Test
    public void shouldAllowAGoodValue() throws IOException {
        JsonParser parser = jsonFactory.createParser("{\"name\": \"DESTROYER\", \"size\": 2}");
        Object actual = deserializer.deserialize(parser, context);
        assertThat(actual).isEqualToComparingFieldByField(DESTROYER);
    }

    @Test
    public void shouldNotAllowABadValueName() throws IOException {
        JsonParser parser = jsonFactory.createParser("{\"name\": \"POOP\", \"size\": 2}");
        Object actual = deserializer.deserialize(parser, context);
        assertThat(actual).isEqualToComparingFieldByField(INVALID_SHIP);
    }

    @Test
    public void shouldNotAllowABadValueSize() throws IOException {
        JsonParser parser = jsonFactory.createParser("{\"name\": \"DESTROYER\", \"size\": 4}");
        Object actual = deserializer.deserialize(parser, context);
        assertThat(actual).isEqualToComparingFieldByField(INVALID_SHIP);
    }

    @Test
    public void shouldBeAbleToHandleTakingInJustName() throws IOException {
        JsonParser parser = jsonFactory.createParser("\"DESTROYER\"");
        Object actual = deserializer.deserialize(parser, context);
        assertThat(actual).isEqualToComparingFieldByField(DESTROYER);
    }

    @Test
    public void shouldBeAbleToHandleTakingInJustAnIncorrectName() throws IOException {
        JsonParser parser = jsonFactory.createParser("\"SCHOONER\"");
        Object actual = deserializer.deserialize(parser, context);
        assertThat(actual).isEqualToComparingFieldByField(INVALID_SHIP);
    }
}
package ga.tianyuge.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonPropertyTest {

    @Data
    @JsonInclude
    public static class TestObject {

        public static class JsonViewTest {}

        @JsonProperty("newName")
        @JsonView(value = JsonViewTest.class)
        private String originalName = "testValue";
    }

    @Test
    public void testJsonProperty() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        String jsonString = objectMapper
                .writerWithView(TestObject.JsonViewTest.class)
                .writeValueAsString(new TestObject());
        System.out.println(jsonString);
//        assertEquals("{\"newName\":\"testValue\"}", jsonString);
    }
}
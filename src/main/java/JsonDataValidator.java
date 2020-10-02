import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Set;

public class JsonDataValidator {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        try (
                InputStream jsonStream = JsonDataValidator.class.getResourceAsStream("validator.json");
                InputStream schemaStream = JsonDataValidator.class.getResourceAsStream("input.json")
        ) {
            JsonNode json = objectMapper.readTree(jsonStream);
            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            Set<ValidationMessage> validationResult = schema.validate(json);

            // print validation errors
            if (validationResult.isEmpty()) {
                System.out.println("no validation errors :-)");
            } else {
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
            }
        }

//        String file = Files.readString(Path.of(JsonDataValidator.class.getClassLoader().getResource("schema.yml").getPath()));
//        System.out.println(convertYamlToJson());
    }

//    private static String convertYamlToJson(String yaml) throws JsonProcessingException {
//        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
//        Object obj = yamlReader.readValue(yaml, Object.class);
//
//        ObjectMapper jsonWriter = new ObjectMapper();
//        return jsonWriter.writeValueAsString(obj);
//    }
}

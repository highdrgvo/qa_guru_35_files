import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFileParsingTest {

    private ClassLoader cl = getClass().getClassLoader();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void jsonFileParsingTest() throws Exception {

        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("example.json"))) {

            JsonNode rootNode = mapper.readTree(reader);

            assertTrue(rootNode.has("squadName"));
            assertEquals("Super tower", rootNode.get("secretBase").asText());
            assertEquals(2016, rootNode.get("formed").asInt());

            JsonNode membersArray = rootNode.get("members");
            assertTrue(membersArray.isArray(), "Members есть массив");

            JsonNode elementInArray = membersArray.get(0);

            assertEquals(29, elementInArray.get("age").asInt());
            assertEquals("Dan Jukes", elementInArray.get("secretIdentity").asText());

            JsonNode powersArray = elementInArray.get("powers");
            assertTrue(powersArray.isArray());
            System.out.println(powersArray);

            List<String> expectedPowers = List.of(
                    "Radiation resistance",
                    "Turning tiny",
                    "Radiation blast"
            );

            for (int i = 0; i < expectedPowers.size(); i++) {
                assertEquals(expectedPowers.get(i), powersArray.get(i).asText());
            }
        }
    }
}

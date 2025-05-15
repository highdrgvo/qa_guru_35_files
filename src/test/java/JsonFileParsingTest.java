import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFileParsingTest {

    // Создаётся экземпляр ClassLoader, который позволяет загружать ресурсы (в данном случае JSON файл)
    private ClassLoader cl = getClass().getClassLoader();
    // Создается экземпляр ObjectMapper для работы с JSON.
    // ObjectMapper — это основной класс библиотеки Jackson, который отвечает за преобразование (парсинг) JSON в Java-объекты и обратно.
    // В данном тесте он используется для чтения и анализа JSON-файла.
    private ObjectMapper mapper = new ObjectMapper();

    // В JUnit тестах можно не ловить исключения — если они возникнут, тест упадёт с ошибкой,
    // но следующие тесты будут выполняться
    // С помощью throws предупреждаем, что метод может выбросить исключение
    @Test
    void jsonFileParsingTest() throws Exception {


        // try - блок кода, в котором может произойти исключение
        // Reader - это абстрактный класс в Java, предназначенный для чтения текстовых данных (символов) из потока. reader — это переменная, которая хранит объект для чтения текста.
        // new InputStreamReader - InputStream читает байты, но JSON — это текст. InputStreamReader преобразует байты в символы (с учётом кодировки, по умолчанию — UTF-8)
        // cl.getResourceAsStream("example.json") - cl - загружает ресурсы, getResourceAsStream - открывает файл example
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("example.json"))) {


            // JsonNode rootNode — корень дерева JSON.
            //  - JsonNode — это абстрактный класс Jackson, представляющий узел JSON.
            //  - rootNode — корневой узел, через который можно получить доступ ко всем данным JSON.
            // .readTree(reader) - парсинг json. Метод readTree() читает JSON из источника (в данном случае — из Reader) и возвращает корневой узел дерева (JsonNode).
            // Как это работает?
            // 1. Jackson получает поток символов (Reader), который читает JSON-файл.
            // 2. Анализирует структуру JSON (объекты, массивы, поля).
            // 3. Строит в памяти дерево узлов JsonNode, где каждый узел соответствует элементу JSON.
            // Итог: JsonNode rootNode = mapper.readTree(reader);
            // Строка
            //  1. Читает JSON из Reader (файла, строки или сети).
            //  2. Парсит его в дерево JsonNode.
            //  3. Возвращает корневой узел, через который можно получить все данные.
            //  4. Не требует создания POJO — идеально для тестов и гибкого парсинга.
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

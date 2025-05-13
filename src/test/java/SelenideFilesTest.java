import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SelenideFilesTest {

    @Test
    void downloadFileTest() throws IOException {

        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloaded = $(".prc-ButtonGroup-ButtonGroup-vcMeG a[href='https://github.com/junit-team/junit5/raw/refs/heads/main/README.md']").download(); // Скачивание файла
        String dataAsString = FileUtils.readFileToString(downloaded, StandardCharsets.UTF_8); // Чтение содержимого файла
        assertTrue(dataAsString.contains("Contributions to JUnit 5 are both welcomed and appreciated."), "Файл не содержит указанный текст"); // Проверка наличия текста в файле
    }


    @Test
    void uploadFileTest() {

        open("https://www.file.io/");
        // любой upload на любом сайте в любом браузере совершается одинаково. Classpath - это по сути моя папка с проектом. Classpath не привязан к конкретной машине, на которой код запускается.
        // В src/test папка resources - это корень Classpath для всех файлов.
        // Папка java - это корень classpath для всех поимх классов.
        $("input[type='file']").uploadFromClasspath("360_F_268313239_A5AB8WJbegba8Y7UcmnUa6qveDgOejlf.jpg");
        actions().pause(15000).perform();
        $(byText("360_F_268313239_A5AB8WJbegba8Y7UcmnUa6qveDgOejlf"));
    }
}

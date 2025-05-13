import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileParsingTest {

    private ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void pdfFileParsingTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloaded = $("a[href='junit-user-guide-5.12.2.pdf']").download();
        PDF pdf = new PDF(downloaded);
        Assertions.assertEquals("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein", pdf.author);

    }

    @Test
    void xlsParsingTest() throws Exception {

        open("https://excelvba.ru/programmes/Teachers");
        File downloaded = $("a[href='https://ExcelVBA.ru/sites/default/files/teachers.xls']").download();

        XLS xls = new XLS(downloaded);

        String actualValue =  xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue();
        assertTrue(actualValue.contains("1. Суммарное количество часов планируемое на штатную по всем разделам плана  должно \n" +
                "составлять примерно 1500 час в год."), "Файл не содержит указанный текст");

    }

    @Test
    void csvFileParsingTest() throws Exception {

        try (InputStream is = cl.getResourceAsStream("exampleCsv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {

            List<String[]> data = csvReader.readAll();

            Assertions.assertEquals(2, data.size());
            Assertions.assertArrayEquals(
                    new String[] {"Selenide", "https://selenide.org"},
                    data.get(0)
            );

            Assertions.assertArrayEquals(
                    new String[] {"JUnit 5", "https://junit.org"},
                    data.get(1)
            );
        }
    }

}

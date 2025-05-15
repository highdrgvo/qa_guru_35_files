import com.codeborne.xlstest.XLS;
import net.sf.jxls.transformer.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileParsingTest {

    // Эта строка кода создаёт экземпляр ClassLoader, который позволяет загружать ресурсы
    // (например, файлы из src/main/resources или src/test/resources) в Java-приложении.
    // ClassLoader - находит ресурсы (файлы из resources/, картинки, конфиги и т. д.).
    // Метод getClassLoader() возвращает загрузчик классов, ответственный за загрузку класса zipFileParsingTest.
    // Этот загрузчик знает, где искать ресурсы.
    // Здесь classLoader — это загрузчик, который загружал zipFileParsingTest.class
    private ClassLoader classLoader = ZipFileParsingTest.class.getClassLoader();


    @Test
    // В JUnit тестах можно не ловить исключения — если они возникнут, тест упадёт с ошибкой,
    // но следующие тесты будут выполняться
    void zipFileParsingTest() throws Exception {

        // ZipInputStream - класс для чтения архивов.
        // new ZipInputStream - создаем специальный поток для чтения архива.
        // try - автоматически закрывает поток zipInputStream после выхода из блока try
        // classLoader.getResourceAsStream("zip_archive.zip") - загружаем файл из ресурсов проекта
        try (ZipInputStream zipInputStream = new ZipInputStream(classLoader.getResourceAsStream("zip_archive.zip"))){

            // ZipEntry представляет один файл внутри ZIP-архива
            // Объявляет переменную entry типа ZipEntry
            ZipEntry entry;


            // Метод getNextEntry() читает следующую запись (файл или директорию) из ZIP-архива.
            // Возвращает объект типа ZipEntry, который содержит информацию о файле (имя, размер, и т. д.).
            // Результат вызова getNextEntry() присваивается переменной entry.

            // Без ZipInputStream вы бы видели только "сырые" байты и не смогли бы понять,
            // где начинается/заканчивается каждый файл.
            // Почему нельзя обойтись обычным InputStream?
            // Обычный InputStream даёт только поток байтов без информации о структуре архива.
            //ZIP-архив имеет сложную структуру (заголовки файлов, сжатие, контрольные суммы),
            // которую ZipInputStream умеет разбирать.
            while((entry = zipInputStream.getNextEntry()) != null ) {
                if (entry.getName().endsWith(".xlsx")) {
                    System.out.println(entry.getName());

                    XLS xls = new XLS(zipInputStream);

                    String xlsFirstCellStringTwo = xls.excel.getSheetAt(0).getRow(1).getCell(4).getStringCellValue();
                    System.out.println(xlsFirstCellStringTwo);
//                    Assertions.assertEquals("Onc", xlsFirstCellStringTwo);



                }
            }
        }
    }

}

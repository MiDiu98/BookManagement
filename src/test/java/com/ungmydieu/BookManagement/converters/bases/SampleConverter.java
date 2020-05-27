package com.ungmydieu.BookManagement.converters.bases;

import com.ungmydieu.BookManagement.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Component
public class SampleConverter extends Converter<String, Integer> {
    @Override
    public Integer convert(String source) throws BadRequestException {
        return Integer.parseInt(source);
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {GsonAutoConfiguration.class})
class ConverterTest {
    @Autowired
    private SampleConverter sampleConverter;

    @Test
    public void test_Convert() {
        String number = "1";
        Integer result = sampleConverter.convert(number);
        assertEquals(result, (Integer) 1);
    }

    @Test
    public void test_convert_List() {
        String[] numbers = new String[]{"1","2","3"};
        List<Integer> result = sampleConverter.convert(Arrays.asList(numbers));
        assertEquals(result, Arrays.asList(1, 2, 3));
    }
}

package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertNotNull;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AOPTest {
    @Autowired
    PdfService pdfService;
    @Test
    public void aopTestReturnvoid(){
        assertNotNull(pdfService);
        pdfService.aopTestMethod1();
    }
    @Test
    public void aopTestReturnString(){
        assertNotNull(pdfService);
        pdfService.apoTestMethodRetString();
    }
    @Test
    public void encodingTest() throws UnsupportedEncodingException {
        String url = "FG logo.jpg";
        String url1 = "zenanalogo.jgp";
        System.out.println(URLEncoder.encode(url, "UTF-8"));
        System.out.println(URLEncoder.encode(url1, "UTF-8"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void optionalTest(){
        Map<Integer,Integer> hashMap = IntStream.range(1,11).boxed().collect(Collectors.toMap(
            Function.identity(), Function.identity()
        ));
        System.out.println(Optional.ofNullable(hashMap.get(11))
                .orElseThrow(()->new IllegalArgumentException("Invalid")));
    }
}

package com.example.demo;

import com.example.demo.Utiliy.Crypto;
import com.example.demo.Utiliy.Utility;
import com.example.demo.product.model.ProductBuilder;
import com.example.demo.product.model.ProductEntity;
import com.google.common.collect.Iterators;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

@Slf4j
public class InputStreamTest {
    private File dir;
    private Path rootPath;
    private Thread deleteThead;
    private InputStream inputStream;

    @Before
    public void setUp() throws FileNotFoundException {
        rootPath = Paths.get("src", "main", "webapp");
        dir = new File(rootPath.toFile(), "/hoho" + Instant.now().getEpochSecond());
        deleteThead = new Thread(() -> {
            if (dir.exists()) {
                System.out.println("delete directory!");
                dir.delete();
            }
        });
        Path resourceDirectory = Paths.get("src", "test", "resources", "hoho1.txt");
        inputStream = new FileInputStream(resourceDirectory.toFile());
    }

    @Test
    public void test() throws IOException {
        File file = new File("\\hoho.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        if (file.isFile() && file.canWrite()) {
            bufferedWriter.write("hoho");
            bufferedWriter.close();
        }
    }

    @Test
    public void testPath() throws IOException {
//        String path = "src/test/resources";
//
//        File file = new File(path);
//        String absolutePath = file.getAbsolutePath();
//
//        System.out.println(absolutePath);
//
//        assertThat(absolutePath.endsWith("src/test/resources"));
        Path resourceDirectory = Paths.get("src", "test", "resources", "hoho2.txt");
        File file = resourceDirectory.toFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            if (file.isFile() && file.canWrite()) {
                bufferedWriter.append("hohoho!!!!!!!!!!!!!!!\n");
                bufferedWriter.append("hohoho");
                bufferedWriter.close();
            }
        } catch (IOException e) {
            // 알아서 bufferedWriter.close를 해주기 때문에 상관은 없다.
        }


        final byte[] buffer = "hohooutput1.txt".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buffer);
        FileOutputStream outputStream = new FileOutputStream(Paths.get("src", "test", "resources", "hohooutput1.txt").toString());
        FileCopyUtils.copy(inputStream, outputStream);
        //        int i=0,remaining = 10;
//        while (remaining > 0) {
//            int l = inputStream.read(buffer,0,4);
//            if(l==-1){
//                System.out.println("HOHO");
//                break;
//            }
//            outputStream.write(buffer,0,l);
//            remaining -=l;
//        }
//        while((i=inputStream.read(buffer))!=-1){
//            System.out.write(buffer,0,i);
//            System.out.println();
//            outputStream.write(buffer,0,i);
//        }
        System.out.println(Paths.get("hoho", "hihi").toString());
    }

    @Test
    public void testByteArrayInputStreamToOutputStream() throws IOException {
        final byte[] buffer = "hohooutput1.txt".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buffer);
        FileOutputStream outputStream = new FileOutputStream(Paths.get("src", "test", "resources", "hohooutput1.txt").toString());
        FileCopyUtils.copy(inputStream, outputStream);
    }

    // FileOutputStream constructor cannot be overridden
    // file isInvalid() is final boolean -> cannot be overridden
    // so add new notFoundFileClass which extends FileOutputStream customizing notFoundClassException to throw FileNotFoundException deliberately
    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundException() throws IOException {
        final byte[] buffer = "hohooutput1.txt".getBytes();
        InputStream inputStream = new ByteArrayInputStream(buffer);
        FileOutputStream outputStream = new notFoundFileClass();
        ((notFoundFileClass) outputStream).notFoundClassException();
        FileCopyUtils.copy(inputStream, outputStream);
    }

    class notFoundFileClass extends FileOutputStream {
        public notFoundFileClass() throws FileNotFoundException {
            super("notFoundFile");
        }

        public void notFoundClassException() throws FileNotFoundException {
            throw new FileNotFoundException("notFoundFileClass");
        }
    }

    @Test
    public void testEnum() throws IllegalAccessException, InstantiationException {
        Object obj = ProductEntity.class.newInstance();
        for (Field f : obj.getClass().getDeclaredFields()) {
            System.out.println(f.getName());
        }
        //        Path rootPath = Paths.get("src","main","webapp");
//        Files
//                .walk(rootPath)
//                .filter(Files::isRegularFile)
//                .forEach(entity->{
//                    BasicFileAttributes attrs;
//                    try {
//                        attrs = Files.readAttributes(entity.toFile().toPath(), BasicFileAttributes.class);
//                        FileTime time = attrs.creationTime();
//                        LocalDateTime fileTime = LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
//                        LocalDateTime now = LocalDateTime.now();
//                        System.out.println( "파일 생성 날짜 및 시간은 다음과 같습니다.: " + fileTime );
//                        if(fileTime.isBefore(now.minusDays(7))){
//                            System.out.println( entity.toFile().getCanonicalPath()+ fileTime );
//                        }
//                    } catch (IOException e) {
//                        log.error("error : {}",e.getMessage());
//                    }
//
//                });
//        Files.walk(rootPath).filter(Files::isDirectory).forEach(entity->{
//            try {
//                System.out.println(entity.toFile().getCanonicalPath());
//                Files.delete(entity);
//            } catch (IOException e) {
//                log.error("error : {}",e.getMessage());
//            }
//        });
    }

    @Test
    public void deleteTest() throws IOException, InterruptedException {
        FileOutputStream outputStream;
        if (!dir.exists()) {
            System.out.println("before dir");
            dir.mkdir();
        }
        Thread.sleep(100);
        deleteThead.run();
        try {
            File file = new File(dir, "/hoho" + Instant.now().getEpochSecond());
            outputStream = new FileOutputStream(file);
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            System.out.println("if dir does not exist, then make dir before writing file!");
            dir.mkdir();
            File file = new File(dir, "/hoho" + Instant.now().getEpochSecond());
            outputStream = new FileOutputStream(file);
            FileCopyUtils.copy(inputStream, outputStream);
        }

    }

    @Test
    public void filterTest() {
        Integer ret = IntStream
                .rangeClosed(1, 10)
                .boxed().parallel()
                .filter(a -> a % 2 == 0)
                .map(a -> a * 2)
                .reduce(0, Integer::sum);
        System.out.println(ret);
        Optional<Integer> aa = Optional.ofNullable(5);
        aa.map(a -> ProductBuilder.builder().id(a).name("hoho").build());

    }

    @Test
    public void testLastIterator() {
        List<Integer> list = IntStream.range(1, 11).boxed().collect(Collectors.toList());
        int last = Optional.ofNullable(Iterators.getLast(list.iterator())).orElseGet(() -> {
            return 0;
        });
        list.add(1);
        list.add(1);
        list.add(2);
//        int first = list.stream()
//                .filter(a->a%11==0)
//                .findFirst()
//                .map(entity->entity*2)
//                .map(entity->entity*3)
//                .map(entity->entity*4)
//                .orElseGet(()->0);
//        System.out.println(last);
//        System.out.println(first);
        Map<Integer, List<Integer>> intMap = list.stream().collect(toMap(Function.identity(),
                entity -> {
                    List<Integer> list1 = new ArrayList<>();
                    list1.add(entity);
                    return list1;
                },
                (a, b) -> {
                    a.addAll(b);
                    return a;
                }
        ));
        intMap.keySet().forEach(key -> System.out.println("key" + key + "value " + intMap.get(key)));
        Map<Integer, List<Integer>> hoho = list.stream().collect(Collectors.groupingBy(Function.identity()));
        System.out.println(hoho);
    }

    @Test
    public void streamTest() {
        Integer start = 10, end = 100;
        IntStream.iterate(1, n -> n + 1)
                .skip(Long.valueOf(start))
                .limit(Long.valueOf(end))
                .filter(a -> a % 10 == 0)
                .forEach(entity -> {
                    System.out.println(entity);
                });
        String url = null;

        if (StringUtils.isEmpty(url)) {
            System.out.println("STring is empty");
        }
    }

    @Test
    public void downloadURLTest() {
        String downloadPath = "http://dev-service.fashiongo.net/va/s/doc/F9E9477B-77F7-4698-BFAF-0C650EE0175A/po/1598222103-100.pdf";
        String requestor = "zenana";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(downloadPath);
        Crypto.encodeBase64UrlSignValue(requestor).ifPresent(entity -> {
            builder.queryParam("sv", entity);
        });

        System.out.println(builder.build().encode().toString());
    }

    @Test
    public void URItest() {
        String url = "http://dev-service.fashiongo.net/doc/sldkfj?hoho=hoho&hehe=hehe";
        System.out.println(Utility.extractUriQueryString(url));
    }
}

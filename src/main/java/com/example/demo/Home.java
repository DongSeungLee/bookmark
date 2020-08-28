package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.example.demo.ChildParent.ChildParentEntity;
import com.example.demo.FashionGo.FashionGoApiHeader;
import com.example.demo.FashionGo.FashionGoApiResponse;
import com.example.demo.ImageBookmark.ImageBookmarkReqeustFactory;
import com.example.demo.ImageBookmark.ImageBookmarkRequest;
import com.example.demo.annotation.MyContextContainer;
import com.example.demo.async.TestService;
import com.example.demo.book.BookService;
import com.example.demo.book.PersonService;
import com.example.demo.book.WidgetService;
import com.example.demo.hoho.Hoho;
import com.example.demo.member.MemberService;
import com.example.demo.member.repository.OrderService;
import com.example.demo.model.*;
import com.example.demo.product.ProductService;
import com.example.demo.request.CreateMemberRequest;
import com.example.demo.security.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Controller
@Slf4j
public class Home {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final Integer pageSize = 30;
    private static final Long buyerId = 30003L;
    private static final String requestUserId = "856DDB1C-B318-4CF6-9E9B-4CC12207FCD0";
    private static final String requestUserName = "test@fashiongo.net";
    //한 페이지당 리스트 개수

    private static final Integer TODAY = 0;
    private static final Integer YESTERDAY = 1;
    private static final Integer LAST7DAYS = 2;

    private final String searchURL = "dev-www.fashiongo.net/";
    private final String tail = "?searchingImageUrl=";

    private String endpoint = "";
    private final static String QUEUE_NAME = "hello";

    private RestTemplate restTemplate;

    private TestService testService;
    private Hoho hoho;

    private String fashionGoApiUrl;
    private final MemberService memberService;

    private final BookService bookService;

    private final ProductService productService;
    private final WidgetService widgetSerivce;
    private final PersonService personService;
    @Autowired
    CacheService cacheSevice;
    @Autowired
    DefaultListableBeanFactory df;

    private HttpClient htmlClient;

    private OrderService orderService;

    private final PdfService pdfService;

    private final MyContextContainer myContextContainer;

    private final TokenAuthenticationService tokenAuthenticationService ;
    @PostConstruct
    public void init() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setSocketTimeout(10000)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setMaxConnPerRoute(50)
                .setMaxConnTotal(50)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        this.restTemplate = new RestTemplate(factory);
    }

    public Home(TestService testService
            , Hoho hoho123
            , @Qualifier(value = "dongseung") DongSeung dongSeung,
                @Value("${fashionGoApiUrl}") String fashionGoApiUrl,
                MemberService memberService,
                BookService bookService,
                ProductService productService,
                WidgetService widgetSerivce,
                PersonService personService,
                PdfService pdfService,
                @Qualifier("serviceHtmlClient") HttpClient htmlClient,
                OrderService orderService,
                MyContextContainer myContextContainer,
                TokenAuthenticationService tokenAuthenticationService
    ) throws InstantiationException, IllegalAccessException {
        this.testService = testService;
        this.hoho = hoho123;
//        System.out.println(this.hoho.getClass().getName());
//        System.out.println(dongSeung.getDriverClassName());
//        System.out.println(dongSeung.getName());
        this.fashionGoApiUrl = fashionGoApiUrl;
        // dongSeung.getHobby().forEach(a -> System.out.println(a));
        this.memberService = memberService;

        // this.memberService.initial();
        this.bookService = bookService;
        //bookService.addBook();
        // bookService.findBook();
        this.productService = productService;
        // productService.initial();
        this.widgetSerivce = widgetSerivce;
        //  widgetSerivce.initialize();
        this.personService = personService;
        // MemberRepository에 suffix (Impl)이 붙은 것을 Concrete Class
        // 로 자동으로 할당한다.

//        System.out.println("memberRepository"+df.getBeanDefinition("MemberRepositoryImpl"));
//        System.out.println("memberRepositoryImpl2"+df.getBeanDefinition("MemberRepositoryImpl"));
        this.pdfService = pdfService;
        this.htmlClient = htmlClient;
        this.orderService = orderService;
        this.myContextContainer = myContextContainer;
        ChildParentEntity entity = this.myContextContainer.get(ChildParentEntity.class);
        System.out.println("ChildParentEntity name : " + entity.getName());
        System.out.println("ChildParentEntity guid : " + entity.getGuid());
        System.out.println("ChildParentEntity parent : " + entity.getParent());
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @GetMapping(value = "/hoho/{fileName}")
    public void download(HttpServletResponse response,
                         @PathVariable("fileName") String fileName) throws IOException {
        Path resourceDirectory = Paths.get("src", "main", "resources", fileName);
        String contentType = Files.probeContentType(resourceDirectory);
        log.info("hoho file contentTYpe : {}", contentType);
        FileInputStream inputStream = new FileInputStream(resourceDirectory.toFile());
        OutputStream outputStream = response.getOutputStream();

        response.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        Path savePath = Paths.get(fileName);
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(savePath.getFileName().toString(), StandardCharsets.UTF_8)
                .build();
        log.info("hoho file contentDisposition : {}", contentDisposition.toString());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        int copiedBytes = FileCopyUtils.copy(inputStream, outputStream);

    }

    @ResponseBody
    @GetMapping("/person/{personid}")
    public ResponseEntity<?> findOne(@PathVariable Integer personid) {
        return personService
                .findOne(personid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "book", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse book(@RequestBody List<Integer> ids) {
        JsonResponse r = new JsonResponse();
        bookService.findByIdAndActive(ids);
        r.setSuccess(true);
        r.setMessage("hooh");
        r.setMessage("wait!");
        return r;
    }

    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse cache() {
        FieldExam fieldExam = new FieldExam();
        fieldExam.setF1(1);
        fieldExam.setF2(2);
        cacheSevice.method(fieldExam);
        JsonResponse r = new JsonResponse();
        r.setMessage("cache");
        r.setSuccess(true);
        r.setData(null);
        r.setReason("hoho");
        return r;
    }


    private void errormethod() {
        throw new NotFoundException("hooh", ResultCode.NOT_FOUND);
    }

    @RequestMapping(value = "/rabbitMQ", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse rabbitMQ() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("nhn");
        factory.setPassword("nhn");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        r.setMessage("rabbitMQ");
        r.setData(null);
        return r;
    }


    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse file(@RequestParam("file") final MultipartFile multipartFile) {
        JsonResponse r = new JsonResponse();
        try {
            memberService.createFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        } catch (Exception e) {
            log.info("error : {}", e.getMessage());
        }
        // System.out.println(swiftAuth.getAccessToken());
        r.setSuccess(true);
        r.setMessage("hooh");
        r.setMessage("wait!");
        r.setData(null);

        return r;
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse test() {
        JsonResponse r = new JsonResponse();
        for (int i = 0; i < 100; i++) {
            testService.func(i);
        }
        r.setSuccess(true);
        r.setMessage("hooh");
        r.setMessage("wait!");
        r.setData(null);
        testService.method();
        throw new NotFoundException("hoho", ResultCode.NOT_FOUND);
        // return r;
    }

    @UserInfoCheck
    @RequestMapping(value = {"/Favorite/bookmark"},
            method = RequestMethod.GET)
    public String init(@RequestParam(value = "category", required = false, defaultValue = "0"
    ) Integer category
            , Model model
            , HttpServletRequest request,
                       HttpServletResponse response) {
        response.setHeader("HOHO", "hoho");
        System.out.println("hpoho" + hoho);
        model.addAttribute("category", category);
        model.addAttribute("todayValue", LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(0, 0, 0, 0)
        ).format(DATE_FORMATTER));
        return "index";
    }

    //
    @GetMapping("/Favorite/bookmarks/init")
    @ResponseBody
    public JsonResponse today(HttpServletRequest request) throws ConnectException {
        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        System.out.println("today " + request.getHeader("Referer"));
        r.setMessage("success");
        endpoint = ImageBookmarkRequest.initEndpoint(buyerId.intValue());
        log.info("FashionGoAPI request : {}", endpoint);
        FashionGoApiResponse<CollectionObject<Integer>> ret
                = execute(new ParameterizedTypeReference<FashionGoApiResponse<CollectionObject<Integer>>>() {
        }, HttpMethod.GET);
        log.info("FashionGoAPI return : {}", ret);
        r.setData(ret.getData().getContents());
        return r;
    }

    @UserInfoCheck
    @GetMapping("/Favorite/error1")
    @ResponseBody
    public JsonResponse error() {
        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        r.setMessage("success!!!!!!");
        hohoMethod();
        return r;
    }

    private void hohoMethod() {
        throw new NotFoundException("nono", ResultCode.NOT_FOUND);

    }

    @UserInfoCheck
    @GetMapping("/Favorite/getbookmarks")
    @ResponseBody
    public JsonResponse Home(Model model,
                             HttpServletRequest request,
                             @RequestParam(value = "pn", required = false, defaultValue = "1") Integer pageNumber,
                             @RequestParam(value = "endDate", required = false) String endDateStr,
                             @RequestParam(value = "category", required = false, defaultValue = "0") Integer category
    ) {


        ImageBookmarkRequest imageBookmarkRequest = ImageBookmarkReqeustFactory.getImageBookmarkRequest(category, endDateStr);

        endpoint = imageBookmarkRequest.makeEndpoint(buyerId.intValue());
        log.info("FashionGoAPI request : {}", endpoint);
        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        r.setMessage("success");

        FashionGoApiResponse<CollectionObject<ImageUrls>> ret
                = execute(new ParameterizedTypeReference<FashionGoApiResponse<CollectionObject<ImageUrls>>>() {
        }, HttpMethod.GET);
        log.info("FashionGoAPI return : {}", ret);

        ObjectMapper objectMapper = new ObjectMapper();
        //이걸 해야 LocalDateTime을 deserialize할 수 있다!

        model.addAttribute("time", LocalDateTime.now());
        model.addAttribute("data", ret.getData());
        BookmarkBody bookmarkBody = new BookmarkBody();
        bookmarkBody.setContents(ret.getData().getContents());
        bookmarkBody.getContents().forEach(a -> {
            try {
                bookmarkBody.getSearchURLs().add("https://" + searchURL + "/" + tail +
                        URLEncoder.encode(a.getCrossOriginImageUrl(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("bookmark search URL encoding error :{}", e.getMessage());
            }
        });
        bookmarkBody.setTotalCount(ret.getData().getTotalCount());
        r.setData(bookmarkBody);

        return r;

    }

    public <T extends FashionGoApiResponse<?>> T execute(ParameterizedTypeReference<T> type, HttpMethod method) {
        Map<String, String> header = FashionGoApiHeader.getHeader(requestUserId, requestUserName);
        ResponseEntity<T> response = execute(endpoint, "", header, method, type);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("Internal Server error");
        }
        if (!response.getBody().getHeader().isSuccessful()) {
            throw new RuntimeException("response is not successful");
        }
        return response.getBody();
    }

    private <T> ResponseEntity<T> execute(String endpoint, String payload, Map<String, String> headerMap, HttpMethod method
            , ParameterizedTypeReference<T> type) {
        HttpHeaders headers = new HttpHeaders();

        headerMap.keySet().forEach(x -> headers.add(x, headerMap.get(x)));

        try {
            log.info("endpoint: {}, payload : {}", endpoint, payload);
            HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);
            ResponseEntity<T> response = restTemplate.exchange(endpoint, method, requestEntity, type);
            log.info("status code:{}, body:{}", response.getStatusCode(), response.toString());
            if (response.getStatusCode() != HttpStatus.OK) {
                log.info("fail to call the api: {}, {}", endpoint, payload);
            } else {
                log.info("response : {}, {}", response, response.getBody());
            }
            return response;
        } catch (Throwable t) {
            log.error("fail to call the api : {}", t.getMessage(), t);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ResponseBody
    @RequestMapping(value = "/Favorite/bookmarks/{bookmarkId}", method = RequestMethod.DELETE)
    public JsonResponse deleteBookmark(@PathVariable Integer bookmarkId) {
        JsonResponse r = new JsonResponse();
        r.setSuccess(false);
        endpoint = ImageBookmarkRequest.deleteEndpoint(buyerId.intValue(), bookmarkId);
        FashionGoApiResponse<Void> ret = execute(new ParameterizedTypeReference<FashionGoApiResponse<Void>>() {
        }, HttpMethod.DELETE);
        log.info("delete bookmark : {}", ret);
        r.setSuccess(true);
        r.setMessage("success");
        return r;
    }

    @ResponseBody
    @RequestMapping(value = "/member/create", method = RequestMethod.POST)
    public JsonResponse createMember(@RequestBody CreateMemberRequest request) {

        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        memberService.createMember(request);
        return r;
    }

    @ResponseBody
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public JsonResponse findeMember(@RequestBody List<Integer> request) {

        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        memberService.findMembers(request);
        return r;
    }

    @RequestMapping(value = "/scroll", method = RequestMethod.GET)
    public String scroll() {
        return "scroll";
    }

    @ResponseBody
    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public JsonResponse getPDF() {
        JsonResponse r = JsonResponse.builder()
                .success(false)
                .message("hoho")
                .reason("well....")
                .build();
        String html = htmlClient.get("/scroll");
        String dest = "src/main/webapp/";
        String fileName = Instant.now().getEpochSecond() + "hoho.pdf";
        try {
            pdfService.createPdf(html, dest, fileName);
        } catch (DocumentException e) {
            log.error("error : {}", e.getMessage());
        } catch (IOException e) {
            log.error("error : {}", e.getMessage());
        }
        return r;

    }

    private static final String SECRET = "Bookmark";
    private Verification verification = null;

    private Verification verification() throws UnsupportedEncodingException {
        if (verification == null) {
            Algorithm algorithm = Algorithm.HMAC512(SECRET);
            verification = JWT.require(algorithm);
        }
        return this.verification;
    }
    // String으로 보내려면 그냥 ""없이 request body에 넣어야 한다.
    @PostMapping("/JWT")
    @ResponseBody
    public JsonResponse getJWT(@RequestBody String request) {

        JsonResponse r = JsonResponse.builder()
                .success(false)
                .message("hoho")
                .reason("well....")
                .build();
        // 왜 signature를 verify하는데 error가 나는지 도통 모르겠다... ㅠㅜ
        // 계속해서 algorithm이 맞지 않는다고 error 출력
        // algorithm을 아예 동일하게 맞추니 되었다.....
        try {
            DecodedJWT verified = verification()
                    .build()
                    .verify(request);
            Map<String, Claim> claims = verified.getClaims();
            String username = claims.get("username").asString();
            String password = claims.get("password").asString();
            String attr = claims.get("attr").asString();
            log.info("decode jwt username : {}",username);
            log.info("decode jwt password : {}",password);
            log.info("decode jwt attr : {}",attr);
            r.setSuccess(true);
        } catch (UnsupportedEncodingException e) {
            log.warn("getJWT error : {}",e.getMessage());
        }
        catch(SignatureVerificationException e){
            log.warn("Algorithm error : {}",e.getMessage());
        }
        return r;
    }
}

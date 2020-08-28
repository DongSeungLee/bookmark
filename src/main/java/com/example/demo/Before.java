package com.example.demo;

import com.example.demo.model.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
@Slf4j
public class Before {

    @RequestMapping(value = "/fileupload",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse upload(@RequestParam("file") MultipartFile[] file){


        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        r.setMessage("success");
        // file.forEach(a->log.info("file upload :{}",a.getName()));
        for(int i=0;i<file.length;i++){
            log.info("file upload :{}",file[i].getOriginalFilename());
            try{
                saveFile(file[i]);
            }
            catch(IOException e){
                log.error("file error : {}",e.getMessage());
            }
        }

        return r;

    }
    String buildString(String... strings) {
        StringBuilder urlBuilder = new StringBuilder();
        Arrays.stream(strings).forEach(urlBuilder::append);
        return urlBuilder.toString();
    }
    public void saveFile(MultipartFile file) throws IOException {

        String extension = convertMimeTypeToExtension(file.getContentType());

        String randomFilename = RandomStringUtils.randomAlphanumeric(32).toLowerCase();
        String filename = Instant.now().getEpochSecond()+randomFilename+"."+extension;
        Path path = Paths.get( "hoho/","hihi/",filename);

        log.info("save File extentsion : {}",path.toString());
        if(!Files.exists(path.getParent())){
            Files.createDirectories(path.getParent());
        }
        file.transferTo(path.toFile());

        return;
    }
    private String convertMimeTypeToExtension(String mimeType) {
        switch (mimeType.toLowerCase()) {
            case "text/plain":
                return "txt";
            case "image/png":
                return "png";
            case "image/gif":
                return "gif";
        }
        throw new IllegalArgumentException("File must be JPG, GIF, or PNG format.");
    }
}

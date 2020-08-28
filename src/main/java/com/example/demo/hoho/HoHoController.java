package com.example.demo.hoho;

import com.example.demo.model.JsonResponse;
import com.example.demo.model.NotFoundException;
import com.example.demo.model.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

@RestController
public class HoHoController {
    @GetMapping("/Favorite/error")
    @ResponseBody
    public JsonResponse error(){
        JsonResponse r = new JsonResponse();
        r.setSuccess(true);
        r.setMessage("success!!!!!!");
        hohoMethod();
        return r;
    }
    private void hohoMethod(){
        throw new NotFoundException("nono!!!!!!!!!!!!!", ResultCode.NOT_FOUND);
    }
}

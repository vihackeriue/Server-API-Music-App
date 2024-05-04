package com.musicapp.serverapimusicapp.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestAPI {
    @GetMapping("/test")
    public  String testAPI(){
        return  "Success";
    }

//    @RequestMapping(value = "/new", method = RequestMethod.POST)
//    @ResponseBody
//    public NewDTO createNew(@RequestBody NewDTO model) {
//        return model;
//    }
}

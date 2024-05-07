package com.cug.baggage_calculator.controller;

import com.cug.baggage_calculator.service.BaggageService;
import com.cug.baggage_calculator.vo.Result;
import com.cug.baggage_calculator.vo.param.BaggageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("baggage")
public class BaggageController {
    @Autowired
    private BaggageService baggageService;

    @PostMapping("calculate")
    private Result Calculate(@RequestBody BaggageParam baggageParam){
//        System.out.println("accept!");
//        System.out.println(baggageParam.getNormalBaggage());

        return baggageService.CalBaggagePrice(baggageParam);
    }
    @GetMapping("test")

    private Result Test(){
        System.out.println("hello world");
        return null;
    }
}
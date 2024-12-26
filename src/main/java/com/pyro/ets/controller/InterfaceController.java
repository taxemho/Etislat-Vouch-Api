package com.pyro.ets.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public interface InterfaceController {

    @PostMapping("/fetchbill")
    String getFetchBill(@RequestBody String req);
    
    @PostMapping("/savedata")
    String getSaveData(@RequestBody String req);
    
    @PostMapping("/payment")
    String payment(@RequestBody String req);
    
    @GetMapping("/generatecdr")
    String generateCDR();
}


package com.paygateway.controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    @PostMapping("/pay")
    public String pay(){ return "ok"; }
}

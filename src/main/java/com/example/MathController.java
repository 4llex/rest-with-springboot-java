package com.example;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("math")
public class MathController {

  private final AtomicLong counter = new AtomicLong();

  @RequestMapping(value = "/sum/{number1}/{number2}", method = RequestMethod.GET)
  public Double sum(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) {
    return 10.0;
  }

}

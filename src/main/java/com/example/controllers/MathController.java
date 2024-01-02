package com.example.controllers;

import com.example.exceptions.UnsupportedMathOperationException;
import com.example.math.SimpleMath;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static com.example.converters.NumberConverter.convertToDouble;
import static com.example.converters.NumberConverter.isNumeric;

@RestController
@RequestMapping("math")
public class MathController {

  private final AtomicLong counter = new AtomicLong();

  private SimpleMath math = new SimpleMath();

  @RequestMapping(value = "/sum/{number1}/{number2}", method = RequestMethod.GET)
  public Double sum(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return math.sum(convertToDouble(number1), convertToDouble(number2));
  }

  @RequestMapping(value = "/sub/{number1}/{number2}", method = RequestMethod.GET)
  public Double sub(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return math.sub(convertToDouble(number1), convertToDouble(number2));
  }

  @RequestMapping(value = "/multi/{number1}/{number2}", method = RequestMethod.GET)
  public Double multi(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return math.multi(convertToDouble(number1), convertToDouble(number2));
  }

  @RequestMapping(value = "/div/{number1}/{number2}", method = RequestMethod.GET)
  public Double div(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return math.div(convertToDouble(number1), convertToDouble(number2));
  }

  @RequestMapping(value = "/media/{number1}/{number2}", method = RequestMethod.GET)
  public Double media(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return math.media(convertToDouble(number1), convertToDouble(number2));
  }

  @RequestMapping(value = "/squarerRoot/{number}", method = RequestMethod.GET)
  public Double squarerRoot(@PathVariable(value = "number") String number) throws Exception {

    if (!isNumeric(number)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return math.squarerRoot(convertToDouble(number));
  }

}

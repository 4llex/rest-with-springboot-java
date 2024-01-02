package com.example;

import com.example.exceptions.UnsupportedMathOperationException;
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
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return convertToDouble(number1) + convertToDouble(number2);
  }

  @RequestMapping(value = "/sub/{number1}/{number2}", method = RequestMethod.GET)
  public Double sub(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return convertToDouble(number1) - convertToDouble(number2);
  }

  @RequestMapping(value = "/multi/{number1}/{number2}", method = RequestMethod.GET)
  public Double multi(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return convertToDouble(number1) * convertToDouble(number2);
  }

  @RequestMapping(value = "/div/{number1}/{number2}", method = RequestMethod.GET)
  public Double div(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return convertToDouble(number1) / convertToDouble(number2);
  }

  @RequestMapping(value = "/media/{number1}/{number2}", method = RequestMethod.GET)
  public Double media(@PathVariable(value = "number1") String number1,
                    @PathVariable(value = "number2") String number2) throws Exception {

    if (!isNumeric(number1) || !isNumeric(number2)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return (convertToDouble(number1) + convertToDouble(number2))/2;
  }

  @RequestMapping(value = "/squarerRoot/{number}", method = RequestMethod.GET)
  public Double squarerRoot(@PathVariable(value = "number") String number) throws Exception {

    if (!isNumeric(number)) {
      throw new UnsupportedMathOperationException("Please set a numeric value!");
    }
    return Math.sqrt(convertToDouble(number));
  }

  private Double convertToDouble(String strNumber) {
    if (strNumber == null) return 0D;
    String number = strNumber.replaceAll(",", ".");
    if (isNumeric(number)) return Double.parseDouble(number);
    return 0D;
  }

  private boolean isNumeric(String strNumber) {
    if (strNumber == null) return false;
    String number = strNumber.replaceAll(",", ".");
    return number.matches("[-+]?[0-9]*\\.?[0-9]+");
  }

}

package com.packtpub.mmj.rest.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.packtpub.mmj.lib.Calculation;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sousharm
 */
@RestController
@RequestMapping("calculation")
public class CalculationController {

    private static final String PATTERN = "^-?+\\d+\\.?+\\d*$";

    /**
     * Calculates the power of given base and exponent value
     *
     * @param b base
     * @param e exponent
     */
    @RequestMapping("/power")
    public Calculation pow(@RequestParam(value = "base") String b,
        @RequestParam(value = "exponent") String e) {
        List<String> input = Arrays.asList(b, e);
        String powValue;
        if (b != null && e != null && b.matches(PATTERN) && e.matches(PATTERN)) {
            powValue = String.valueOf(Math.pow(Double.valueOf(b), Double.valueOf(e)));
        } else {
            powValue = "Base or/and Exponent is/are not set to numeric value.";
        }
        List<String> output = Arrays.asList(powValue);
        return new Calculation(input, output, "power");
    }

    /**
     * Calculates the square root of given value
     *
     * @param aValue
     */
    @RequestMapping(value = "/sqrt/{value:.+}", method = GET)
    public Calculation sqrt(@PathVariable(value = "value") String aValue) {
        List<String> input = Arrays.asList(aValue);
        String sqrtValue;
        if (aValue != null && aValue.matches(PATTERN)) {
            sqrtValue = String.valueOf(Math.sqrt(Double.valueOf(aValue)));
        } else {
            sqrtValue = "Input value is not set to numeric value.";
        }
        List<String> output = Arrays.asList(sqrtValue);
        return new Calculation(input, output, "sqrt");
    }
}

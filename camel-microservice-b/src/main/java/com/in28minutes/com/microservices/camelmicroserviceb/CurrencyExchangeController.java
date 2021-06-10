/**
 * 
 */
package com.in28minutes.com.microservices.camelmicroserviceb;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author renan
 *
 */

@RestController
public class CurrencyExchangeController {

	@GetMapping("/currency-exchange/from/{from}/tp/{to}")
	public CurrencyExchange findConversionValue(@PathVariable String from, @PathVariable String to) {
		return new CurrencyExchange(1001L, from, to, BigDecimal.TEN);
	}

}

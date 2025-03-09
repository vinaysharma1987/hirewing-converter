package com.hirewing.india.core_hirewing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class Controller {
	@Value("${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}")
	private String currencyExchangeServiceHost;
	
	@Autowired
    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello World";
    }
    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public String hello2() {
        return "Hello World";
    }
    @GetMapping("/convert")
    public String convert(@RequestParam int input) {
        return String.valueOf(input * 87) + " Rupees";
    }

    @GetMapping("/convertFromOther/{amount}")
    public ResponseEntity<String> convertFromOther(@PathVariable double amount) {
		
		 String url = UriComponentsBuilder.fromHttpUrl(currencyExchangeServiceHost + ":9090")
                .path("/converter/inrToDollar/{from}")
                .buildAndExpand(amount)
                .toUriString();
				
				
       return ResponseEntity.ok(restTemplate.getForEntity(url, String.class).getBody());
    }
	
	@GetMapping("/convertFromOther2/{amount}")
	public ResponseEntity<String> convertFromOther2(@PathVariable double amount) {
    // Ensure URL is correctly formatted
   // String baseUrl = currencyExchangeServiceHost.startsWith("http") ? currencyExchangeServiceHost : "http://" + currencyExchangeServiceHost;

    //String url = UriComponentsBuilder.fromHttpUrl(baseUrl + ":9090")
     //       .path("/converter/inrToDollar?amount={amount}")
     //       .buildAndExpand(amount)
     //       .toUriString();
			
	String uri2 = "http://currency-converter:9090/converter/inrToDollar?amount="+amount;
    return ResponseEntity.ok(restTemplate.getForEntity(uri2, String.class).getBody());
}
}

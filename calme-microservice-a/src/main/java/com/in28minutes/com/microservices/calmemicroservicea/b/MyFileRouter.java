/**
 * 
 */
package com.in28minutes.com.microservices.calmemicroservicea.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author renan
 *
 */
@Component
public class MyFileRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:files/input")
		.log("${body}")
		.to("file:files/output");
		
	}
	
}

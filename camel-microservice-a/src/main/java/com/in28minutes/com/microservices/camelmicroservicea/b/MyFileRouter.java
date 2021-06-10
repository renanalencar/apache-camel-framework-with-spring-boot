/**
 * 
 */
package com.in28minutes.com.microservices.camelmicroservicea.b;

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
		.routeId("Files-Input-Route")
		.transform().body(String.class)
		.choice()
			.when(simple("${file:ext} ends with 'xml'"))
				.log("XML FILE")
			.when(simple("${body} contains 'USD'"))
				.log("NOT XML FILE BUT CONTAINS USD")
			.otherwise()
				.log("NOT XML FILE")
		.end()
		.log("${messageHistory} ${headers.CamelFileAbsolute}")
		.to("file:files/output");
		
	}
	
}

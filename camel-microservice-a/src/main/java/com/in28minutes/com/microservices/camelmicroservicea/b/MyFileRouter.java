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
			.when(simple("${file:ext} == 'xml'"))
				.log("XML FILE")
			.when(simple("${body} contains 'USD'"))
				.log("NOT XML FILE BUT CONTAINS USD")
			.otherwise()
				.log("NOT XML FILE")
		.end()
		.to("direct://log-file-values")
		.to("file:files/output");
		
		from("direct:log-file-values")
		.log("${messageHistory} ${file:absolute.path}")
		.log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
		.log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
		.log("${file:size} ${file:modified}")
		.log("${routeId} ${camelId} ${body}");
		
	}
	
}

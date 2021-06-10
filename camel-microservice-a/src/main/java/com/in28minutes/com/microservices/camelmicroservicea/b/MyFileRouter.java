/**
 * 
 */
package com.in28minutes.com.microservices.camelmicroservicea.b;

import java.util.Map;

import org.apache.camel.ExchangeProperties;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

/**
 * @author renan
 *
 */

@Component
public class MyFileRouter extends RouteBuilder {
	
	@Autowired
	private DeciderBean deciderBean;

	@Override
	public void configure() throws Exception {
		from("file:files/input")
		.routeId("Files-Input-Route")
		.transform().body(String.class)
		.choice()
			.when(simple("${file:ext} == 'xml'"))
				.log("XML FILE")
			//.when(simple("${body} contains 'USD'"))
			.when(method(deciderBean))	
				.log("NOT XML FILE BUT CONTAINS USD")
			.otherwise()
				.log("NOT XML FILE")
		.end()
//		.to("direct://log-file-values")
		.to("file:files/output");
		
		from("direct:log-file-values")
		.log("${messageHistory} ${file:absolute.path}")
		.log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
		.log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
		.log("${file:size} ${file:modified}")
		.log("${routeId} ${camelId} ${body}");
		
	}
	
}

@Component
class DeciderBean {
	
	Logger logger = LoggerFactory.getLogger(DeciderBean.class);
	
	public boolean isThisConditionMet(String body, @Headers Map<String, String> headers, @ExchangeProperties Map<String, String> properties) {
		logger.info("{}", body, headers, properties);
		return true;
	}
}

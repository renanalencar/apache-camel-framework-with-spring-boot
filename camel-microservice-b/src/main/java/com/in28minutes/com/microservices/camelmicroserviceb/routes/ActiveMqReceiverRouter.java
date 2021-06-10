/**
 * 
 */
package com.in28minutes.com.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author renan
 *
 */
@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("activemq:my-activemq-queue")
		.to("log:received-message-from-active-qm");
	}

}

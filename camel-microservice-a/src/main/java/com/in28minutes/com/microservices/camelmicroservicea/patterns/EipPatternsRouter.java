package com.in28minutes.com.microservices.camelmicroservicea.patterns;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EipPatternsRouter extends RouteBuilder {
	
	@Autowired
	private SplitterComponent splitter;

	@Override
	public void configure() throws Exception {
		//Pipeline
		//Content Based Routing - choice()
		//Multicast
		
//		from("timer:multicast?period=10000")
//		.multicast()
//		.to("log:something1", "log:something2", "log:something3");
		
//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
//		.to("activemq:slipt-queue");
		
		
		//Message, Message2, Message3
		from("file:files/csv")
		.convertBodyTo(String.class)
//		.split(body(), ",")
		.split(method(splitter))
		.to("activemq:slipt-queue");
	}

}

@Component
class SplitterComponent {
	public List<String> splitInput(String body) {
//		return List.of("ABC", "DEF", "GHI"); // List.of() faz parte do Java 9
		return Arrays.asList("ABC", "DEF", "GHI");
	}
}
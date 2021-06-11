package com.in28minutes.com.microservices.camelmicroservicea.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in28minutes.com.microservices.camelmicroservicea.CurrencyExchange;

@Component
public class EipPatternsRouter extends RouteBuilder {
	
	@Autowired
		private SplitterComponent splitter;

	/**
	 * @author renan
	 *
	 */
	public class ArrayListAggregationStrategy implements AggregationStrategy {

		//1,2,3
		//null, 1 => [1]
		//1, 2 => [1,2]
		//[1,2], 3 => [1,2,3]
		
		@Override
		public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
			Object newBody = newExchange.getIn().getBody();
			ArrayList<Object> list = null;
			if (oldExchange == null) {
				list = new ArrayList<Object>();
				list.add(newBody);
				newExchange.getIn().setBody(list);
				return newExchange;
			} else {
				list = oldExchange.getIn().getBody(ArrayList.class);
				list.add(newBody);
				return oldExchange;
			}
		}

	}

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
//		from("file:files/csv")
//		.convertBodyTo(String.class)
////		.split(body(), ",")
//		.split(method(splitter))
//		.to("activemq:slipt-queue");
		
		//Aggregate
		//Messages => Aggregate => Endpoint
		//to , 3
		from("file:files/aggregate-json")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
		.completionSize(3)
		.completionTimeout(HIGHEST)
		.to("log:aggregate-json");
		
	}

}

@Component
class SplitterComponent {
	public List<String> splitInput(String body) {
//		return List.of("ABC", "DEF", "GHI"); // List.of() faz parte do Java 9
		return Arrays.asList("ABC", "DEF", "GHI");
	}
}
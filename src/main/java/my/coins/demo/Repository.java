package my.coins.demo;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Repository {

	private Map<String, ExchangeContext> exchanges = Maps.newConcurrentMap();


	public ExchangeContext getExchange(String name) {

		if (!exchanges.containsKey(name)) {
			ExchangeContext exchangeContext = new ExchangeContext(name);
			exchanges.put(name, exchangeContext);
		}

		return exchanges.get(name);
	}


}

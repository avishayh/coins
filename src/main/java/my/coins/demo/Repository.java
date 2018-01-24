package my.coins.demo;

import com.google.common.collect.Maps;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.util.Map;
import java.util.stream.Collectors;

public class Repository {

	private Map<String, ExchangeContext> exchanges = Maps.newConcurrentMap();

	public ExchangeContext getExchange(String name) {

		if (!exchanges.containsKey(name)) {
			ExchangeContext exchangeContext = new ExchangeContext(name);
			exchanges.put(name, exchangeContext);
		}

		return exchanges.get(name);
	}


	public Map<String, Ticker> currentTickerInAllExchanges(CurrencyPair currencyPair) {
		return exchanges.values()
				.stream()
				.collect(Collectors.toMap(ExchangeContext::getName, x -> x.getCurrentTicker(currencyPair)));

	}
}

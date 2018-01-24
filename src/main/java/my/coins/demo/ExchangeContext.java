package my.coins.demo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExchangeContext {

	private static final Comparator<Ticker> DATE_ASC_COMPARATOR = Comparator.comparingLong(t -> t.getTimestamp().getTime());

	private Exchange exchange;

	private Multimap<CurrencyPair, Ticker> tickersByCurrency = ArrayListMultimap.create();

	private final String name;

	public ExchangeContext(String name) {
		this.name = name;
	}

	public void addTicker(Ticker ticker, LocalDateTime time) {
		tickersByCurrency.put(ticker.getCurrencyPair(), ticker);
	}

	public Ticker getCurrentTicker(CurrencyPair currencyPair) {
		return getTickers(currencyPair)
				//once sorted DESC time (first is newest)
				.findFirst()
				//if not found return null
				.orElse(null);
	}

	public List<Ticker> getTickerHistory(CurrencyPair currencyPair) {
		return getTickers(currencyPair).collect(Collectors.toList());
	}

	private Stream<Ticker> getTickers(CurrencyPair currencyPair) {
		return tickersByCurrency.get(currencyPair)
				.stream()
				//should we sort now or when inserting the data ??
				.sorted(DATE_ASC_COMPARATOR.reversed())
				//filter out tickers after the currentTime (for simulator needs)
				.filter(ticker -> !ticker.getTimestamp().after(DateUtil.now()));
	}

	public String getName() {
		return name;
	}

	public Exchange getExchange() {
		return exchange;
	}

}

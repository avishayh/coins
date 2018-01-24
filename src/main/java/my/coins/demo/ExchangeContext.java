package my.coins.demo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public class ExchangeContext {

	private Exchange exchange;

	private Map<LocalDateTime, Ticker> tickersHistory = Maps.newHashMap();
	private Multimap<CurrencyPair, Ticker> tickersByCurrency = ArrayListMultimap.create();

	//	private LocalDateTime currentTime;
	private Date currentTime = new Date();

	private final String name;

	public ExchangeContext(String name) {
		this.name = name;
	}

	public void addTicker(Ticker ticker, LocalDateTime time) {
		tickersHistory.put(time, ticker);
		tickersByCurrency.put(ticker.getCurrencyPair(), ticker);
	}

	public Ticker getCurrentTicker(CurrencyPair currencyPair) {
		Ticker ticker1 = tickersByCurrency.get(currencyPair)
				.stream()
				.filter(ticker -> !ticker.getTimestamp().after(currentTime))
				.findFirst().get();


		return ticker1;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getName() {
		return name;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public Map<LocalDateTime, Ticker> getTickersHistory() {
		return tickersHistory;
	}

}

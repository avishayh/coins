package my.coins.demo;

import com.google.common.collect.Maps;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.time.LocalDateTime;
import java.util.Map;

public class ExchangeContext {

	private Exchange exchange;
	private Map<LocalDateTime, Ticker> tickersHistory = Maps.newHashMap();

	private LocalDateTime currentTime;
	private Ticker currentTicker;

	private final String name;

	public ExchangeContext(String name) {
		this.name = name;
	}

	public void addTicker(Ticker ticker, LocalDateTime time) {
		tickersHistory.put(time, ticker);
	}

	public void setCurrentTime(LocalDateTime currentTime) {
		this.currentTime = currentTime;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public Map<LocalDateTime, Ticker> getTickersHistory() {
		return tickersHistory;
	}

	public LocalDateTime getCurrentTime() {
		return currentTime;
	}

	public Ticker getCurrentTicker() {
		return currentTicker;
	}
}

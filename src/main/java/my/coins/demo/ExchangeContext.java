package my.coins.demo;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.time.LocalDateTime;
import java.util.Map;

public class ExchangeContext {

	private Exchange exchange;
	private Map<LocalDateTime, Ticker> tickersHistory;

	private LocalDateTime currentTime;
	private Ticker currentTicker;


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

package my.coins.demo.service;

import com.splunk.*;
import my.coins.demo.Constants;
import my.coins.demo.ExchangeContext;
import my.coins.demo.Repository;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Component
public class TickerService {

	private final Service service;
	private final Repository repository;

	public TickerService(Service service, Repository repository) {
		this.service = service;
		this.repository = repository;
	}

	public Repository getRepository() {

		try {
			JobExportArgs args = new JobExportArgs();
			args.setEarliestTime("-2d");
			args.setLatestTime("now");
			args.setOutputMode(JobExportArgs.OutputMode.JSON);

			InputStream results = service.export(Constants.GET_TICKERS, args);

			ResultsReaderJson readerJson = new ResultsReaderJson(results);
			readerJson.iterator().forEachRemaining(event -> {

				String exchangeName = event.get("name");
				ExchangeContext exchange = repository.getExchange(exchangeName);

				LocalDateTime time = LocalDateTime.parse(event.get("time"), Constants.FORMATTER);
				Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());

				Ticker ticker = new Ticker.Builder()
						.currencyPair(getCurrencyPair(event))
						.ask(toBigDecimal(event, "ask"))
						.bid(toBigDecimal(event, "bid"))
						.high(toBigDecimal(event, "high"))
						.open(toBigDecimal(event, "open"))
						.low(toBigDecimal(event, "low"))
						.last(toBigDecimal(event, "last"))
						.volume(toBigDecimal(event, "volume"))
						.quoteVolume(toBigDecimal(event, "quoteVolume"))
						.timestamp(date)
						.build();


				exchange.addTicker(ticker, time);

				System.out.println("exchange = " + exchange);
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private CurrencyPair getCurrencyPair(Event event) {
		String currencyPair = event.get("currencyPair");
		if (currencyPair.length() == 6) {
			return new CurrencyPair(
					currencyPair.substring(0, 3),
					currencyPair.substring(3, 6));
		}
		return null;
	}

	private BigDecimal toBigDecimal(Event event, String key) {
		String value = event.get(key);
		return value != null && !value.equals("null") ? new BigDecimal(value) : null;
	}

}

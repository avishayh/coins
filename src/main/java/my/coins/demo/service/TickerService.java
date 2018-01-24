package my.coins.demo.service;

import com.splunk.*;
import my.coins.demo.CoinUtil;
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


@Component
public class TickerService {

	private final Service splunkService;

	public TickerService(Service splunkService) {
		this.splunkService = splunkService;
	}

	public Repository getRepository() {

		Repository repository = new Repository();
		try {
			JobExportArgs args = new JobExportArgs();
			args.setEarliestTime("-2d");
			args.setLatestTime("now");
			args.setOutputMode(JobExportArgs.OutputMode.JSON);

			InputStream results = splunkService.export(Constants.GET_TICKERS, args);

			ResultsReaderJson readerJson = new ResultsReaderJson(results);
			readerJson.iterator().forEachRemaining(event -> {

				String exchangeName = event.get("name");
				ExchangeContext exchange = repository.getExchange(exchangeName);

				LocalDateTime time = LocalDateTime.parse(event.get("time"), Constants.FORMATTER);
				Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());

				Ticker ticker = new Ticker.Builder()
						.currencyPair(CoinUtil.getCurrencyPair(event.get("currencyPair")))
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

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return repository;
	}

	private BigDecimal toBigDecimal(Event event, String key) {
		String value = event.get(key);
		return value != null && !value.equals("null") ? new BigDecimal(value) : null;
	}

}

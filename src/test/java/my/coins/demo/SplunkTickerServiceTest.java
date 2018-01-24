package my.coins.demo;

import my.coins.demo.config.Application;
import my.coins.demo.service.TickerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SplunkTickerServiceTest {
	private static final Logger log = LoggerFactory.getLogger(SplunkTickerServiceTest.class);

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Autowired
	private TickerService tickerService;

	@Test
	public void tickerServiceTest() throws ParseException {
		Repository repository = tickerService.getRepository();
		Map<String, Ticker> tickerMap = repository.currentTickerInAllExchanges(CurrencyPair.XRP_BTC);
		log.info("lets see one currency in all the exchanges");
		tickerMap.entrySet().forEach(entry -> {
			log.info(String.valueOf(entry));
		});

		//change current time to see it we get a different ticker
		ExchangeContext exchange = repository.getExchange(Constants.BINANCE);
		String time = "23/01/2018 11:38:00";
		DateUtil.setSimulationTimestamp(simpleDateFormat.parse(time));
		Ticker currentTicker = exchange.getCurrentTicker(CurrencyPair.XRP_BTC);
		log.info(String.format("closest ticker date found is [%s]  requested date is [%s] ", currentTicker.getTimestamp(), time));

		log.info(String.format("lets see all the history - current date is [%s]", time));
		List<Ticker> tickerHistory = exchange.getTickerHistory(CurrencyPair.XRP_BTC);
		tickerHistory.forEach(ticker -> log.info(String.format("ticker [date=%s] [last=%s]", ticker.getTimestamp(), ticker.getLast())));
	}

}

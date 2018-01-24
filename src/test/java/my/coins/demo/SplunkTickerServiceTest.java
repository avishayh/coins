package my.coins.demo;

import my.coins.demo.config.Application;
import my.coins.demo.service.TickerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SplunkTickerServiceTest {

	@Autowired
	private	TickerService tickerService;

	@Test
	public void tickerServiceTest() {
		Repository repository = tickerService.getRepository();
		Map<String, Ticker> tickerMap = repository.currentTickerByExchange(CurrencyPair.XRP_BTC);
		Ticker ticker = tickerMap.get(Constants.BINANCE);
		System.out.println("ticker = " + ticker);
	}

}

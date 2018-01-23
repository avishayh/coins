package my.coins.demo.task;

import my.coins.demo.service.ExchangeTickerService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Profile("!test")
public class ExchangeTask implements ApplicationContextAware {


	private ApplicationContext applicationContext;

	@Autowired
	private ExchangeTickerService exchangeTickerService;

	@Autowired
	private List<Exchange> exchangeList;

	@Autowired
	private BinanceExchange binanceExchange;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Scheduled(cron = "* * * * * *")
	public void tickerEachSecond() {

		ExecutorService executorService = Executors.newFixedThreadPool(exchangeList.size());
		for (Exchange exchange : exchangeList) {
			CurrencyPair[] pairs = {CurrencyPair.XRP_BTC, CurrencyPair.BTC_USDT, CurrencyPair.BTC_USD};
			executorService.submit(() -> {
				for (final CurrencyPair pair : pairs) {
					if (exchange.getExchangeSymbols().contains(pair)) {
						exchangeTickerService.printCurrentTicker(exchange, pair);
					}
				}
			});
		}
	}

	@Scheduled(cron = "0 * * * * *")
	public void binancePricesEachMinute() {
		exchangeTickerService.printCurrentTicker(binanceExchange);
	}

}

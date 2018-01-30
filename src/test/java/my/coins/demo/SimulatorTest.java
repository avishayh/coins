package my.coins.demo;

import my.coins.demo.config.Application;
import my.coins.demo.service.Simulator;
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

import java.util.Date;
import java.util.Stack;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SimulatorTest {
	private static final Logger log = LoggerFactory.getLogger(SimulatorTest.class);

	@Autowired
	private TickerService tickerService;

	@Test
	public void runFirstSimulator() {
		Simulator simulator = Simulator.newBuilder()
				.simulatorTime(DateUtil.getDate("22/01/2018"))
				.build(tickerService.getRepository());

		final StringBuilder sb = new StringBuilder(1600);

		Stack<Ticker> tickers = new Stack<>();

		simulator.simulate(repository -> {

			Ticker currentTicker = repository.getExchange(Constants.BINANCE).getCurrentTicker(CurrencyPair.BTC_USD);

			Date now = DateUtil.now();
			if (currentTicker != null) {

				if (tickers.isEmpty() || !tickers.peek().getLast().equals(currentTicker.getLast())) {
					sb.append("time is ").append(DateUtil.toString(now)).append("\n");
					sb.append(String.format("\tcurrent ticker price is [%s] time=[%s]\n", currentTicker.getLast(), currentTicker.getTimestamp()));
					tickers.add(currentTicker);
				}
			}

		});
		log.info("Finished simulation with output:\n" + sb);
	}

}

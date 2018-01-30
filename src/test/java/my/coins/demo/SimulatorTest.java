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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SimulatorTest {
	private static final Logger log = LoggerFactory.getLogger(SimulatorTest.class);

	@Autowired
	private TickerService tickerService;

	@Test
	public void runFirstSimulator() {
		Repository repository = tickerService.getRepository();


		Simulator simulator = Simulator.newBuilder()
				.simulatorTime(DateUtil.getDate("22/01/2018"))
				.build(repository);

		final StringBuilder sb = new StringBuilder(1600);
		simulator.simulate(repository1 -> {

			Ticker currentTicker = repository1.getExchange(Constants.BINANCE)
					.getCurrentTicker(CurrencyPair.BTC_USDT);
			Date now = DateUtil.now();
			sb.append("time is ").append(now).append("\n");
			if (currentTicker != null) {
				sb.append(String.format("\tcurrent ticker price is [%s] time=[%s]\n", currentTicker.getLast(), currentTicker.getTimestamp()));
			} else {
				sb.append("\tCannot find ticker.\n");
			}
		});
		log.info("Finished simulation with output:\n" + sb);
	}

}

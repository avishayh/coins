package my.coins.demo;

import my.coins.demo.service.TickerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SplunkTickerServiceTest {

	@Autowired
	private	TickerService tickerService;

	@Test
	public void tickerServiceTest() {
		tickerService.getAllTickers();
	}

}

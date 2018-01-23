package my.coins.demo.service;

import com.splunk.Job;
import com.splunk.JobExportArgs;
import com.splunk.ResultsReaderJson;
import com.splunk.Service;
import my.coins.demo.Constants;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class TickerService {

	private final Service service;

	public TickerService(Service service) {
		this.service = service;
	}

	public List<Ticker> getAllTickers() {

		try {
			JobExportArgs args = new JobExportArgs();
			args.setEarliestTime("-2d");
			args.setLatestTime("now");
			args.setOutputMode(JobExportArgs.OutputMode.JSON);

			InputStream results = service.export(Constants.GET_TICKERS, args);

			ResultsReaderJson readerJson = new ResultsReaderJson(results);
			readerJson.iterator().forEachRemaining(event->{
				System.out.println("event = " + event);			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

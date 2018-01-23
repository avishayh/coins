package my.coins.demo;

import java.time.format.DateTimeFormatter;

public interface Constants {

	String GET_TICKERS = "search host=yoga920 sourcetype=log4j Ticker \n" +
			"| convert timeformat=\"%d/%m/%Y %H:%M:%S\" ctime(_time) as time \n" +
			"| table time name currencyPair open last bid ask high low avg volume";

	DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
}

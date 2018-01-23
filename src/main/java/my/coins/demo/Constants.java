package my.coins.demo;

public interface Constants {

	String GET_TICKERS = "search host=yoga920 sourcetype=log4j Ticker | table _time name currencyPair open last bid ask high low avg volume";
}

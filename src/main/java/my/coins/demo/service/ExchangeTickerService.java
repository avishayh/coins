package my.coins.demo.service;

import my.coins.demo.CoinUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.dto.marketdata.BinanceSymbolPrice;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static my.coins.demo.CoinUtil.*;

@Service
public class ExchangeTickerService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeTickerService.class);

    public void printCurrentTicker(Exchange exchange, CurrencyPair currencyPair) {

        Ticker ticker = null;
        try {
            ticker = exchange.getMarketDataService().getTicker(currencyPair);
            logger.info(String.format("[name=%s] %s", exchange.getExchangeSpecification().getExchangeName(), toStringSymbol(ticker.toString())));
        } catch (Exception e) {
            printException(exchange, e);
        }

    }

    public void printCurrentTicker(Exchange exchange) {
        try {
            BinanceMarketDataService dataService = (BinanceMarketDataService) exchange.getMarketDataService();
            List<BinanceSymbolPrice> binanceSymbolPrices = dataService.tickerAllPrices();

            binanceSymbolPrices.forEach(bsp->{
                logger.info(String.format("[name=%s] [symbol=%s] [price=%s]",
                        exchange.getExchangeSpecification().getExchangeName(),
                        toStringSymbol(bsp.symbol),bsp.price));
            });
        } catch (IOException e) {
            printException(exchange, e);

        }
    }

    private PrintStream printException(Exchange exchange, Exception e) {
        return System.err.printf("exchange=%s %s %n", exchange.getExchangeSpecification().getExchangeName(), ExceptionUtils.getRootCauseMessage(e));
    }


}

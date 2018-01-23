package my.coins.demo.config;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.hitbtc.v2.HitbtcExchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {


    @Bean
    Exchange binanceExchange() {
        return ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
    }

    //    @Bean
    Exchange coinbaseExchange() {
        return ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    }

    @Bean
    Exchange bitfinixExchange() {
        return ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());
    }

    @Bean
    Exchange kraken() {
        return ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
    }

    @Bean
    Exchange bitstampExchange() {
        return ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    }

    @Bean
    Exchange hitBTC() {
        return ExchangeFactory.INSTANCE.createExchange(HitbtcExchange.class.getName());
    }

}

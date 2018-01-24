package my.coins.demo;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.splunk.Event;
import org.knowm.xchange.currency.CurrencyPair;

import java.text.SimpleDateFormat;

public final class CoinUtil {

	private static final LoadingCache<String, CurrencyPair> currencies = CacheBuilder.newBuilder().build(new CacheLoader<String, CurrencyPair>() {
		@Override
		public CurrencyPair load(String symbol) {
			String currencyPair = toStringSymbol(symbol);
			if (currencyPair.length() == 6) {
				return new CurrencyPair(
						currencyPair.substring(0, 3),
						currencyPair.substring(3, 6));
			}
			throw new RuntimeException("not supported " + symbol);
		}
	});

	private CoinUtil() {
	}

	public static CurrencyPair getCurrencyPair(String symbol) {
		return currencies.getUnchecked(symbol);
	}

	public static String toStringSymbol(String symbol) {
		return symbol
				.replace("USDT", "USD")
				.replace("/", "");
	}
}

package my.coins.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

	private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =
			ThreadLocal.withInitial(() -> new SimpleDateFormat("dd/MM/yyyy"));

	private static final ThreadLocal<SimpleDateFormat> DATE_AND_TIME_FORMAT =
			ThreadLocal.withInitial(() -> new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));

	private static final ThreadLocal<Date> simulatorTimestamp = new ThreadLocal<>();

	private DateUtil() {
	}

	public static Date now() {
		Date date = simulatorTimestamp.get();
		return date != null ? date : new Date();
	}

	public static void setSimulationTimestamp(Date date) {
		simulatorTimestamp.set(date);

	}

	public static String toString(Date date) {
		return DATE_AND_TIME_FORMAT.get().format(date);
	}


	public static Date getDate(String date) {
		try {
			return DATE_FORMAT.get().parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}

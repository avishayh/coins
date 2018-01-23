package my.coins.demo.service;

import my.coins.demo.ExchangeContext;

import java.time.LocalDateTime;

public class Simulator {

	private boolean simulationRunning;
	private ExchangeContext currentContext;


	int simulationSpeed = 1000;

	public void simulate(CoinAlgorithem algorithem) {

		startSimulation();

		while (isSimulationRunning()) {
			ExchangeContext coinContext = getCurrentContext();

			algorithem.calculate(coinContext);
			nextStep();
		}
	}

	private void startSimulation() {
		ExchangeContext coinContext = new ExchangeContext();

	}

	private void nextStep() {
		currentContext.setCurrentTime(LocalDateTime.now());
	}

	public boolean isSimulationRunning() {
		return simulationRunning;
	}

	public ExchangeContext getCurrentContext() {
		return currentContext;
	}

}

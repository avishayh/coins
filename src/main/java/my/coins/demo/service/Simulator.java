package my.coins.demo.service;

import my.coins.demo.DateUtil;
import my.coins.demo.ExchangeContext;
import my.coins.demo.Repository;

import java.time.LocalDateTime;
import java.util.Date;

public class Simulator {

	private final Repository repository;

	private final int simulationSpeed;
	private final Date simulatorTime;
	private final Date endSimulatorTime;

	public Simulator(Repository repository, int simulationSpeed, Date simulatorTime, Date endSimulatorTime) {
		this.repository = repository;
		this.simulationSpeed = simulationSpeed;
		this.simulatorTime = simulatorTime;
		this.endSimulatorTime = endSimulatorTime;
	}

	public void simulate(CoinAlgorithem algorithem) {

		//setting back time to start of simulation
		DateUtil.setSimulationTimestamp(simulatorTime);

		while (isSimulationRunning()) {
			algorithem.calculate(repository);
			nextStep();
		}
	}

	private void nextStep() {
		simulatorTime.setTime(simulatorTime.getTime() + simulationSpeed);
	}

	private boolean isSimulationRunning() {
		return simulatorTime.before(endSimulatorTime);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private int simulationSpeed = 1000;
		private Date simulatorTime;
		private Date endSimulatorTime = new Date();

		public Builder simulationSpeed(int simulationSpeed) {
			this.simulationSpeed = simulationSpeed;
			return this;
		}

		public Builder simulatorTime(Date simulatorTime) {
			this.simulatorTime = simulatorTime;
			return this;
		}

		public Builder endSimulatorTime(Date endSimulatorTime) {
			this.endSimulatorTime = endSimulatorTime;
			return this;
		}

		public Simulator build(Repository repository) {
			return new Simulator(repository, simulationSpeed, simulatorTime, endSimulatorTime);
		}
	}
}

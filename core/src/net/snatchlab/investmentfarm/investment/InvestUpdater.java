package net.snatchlab.investmentfarm.investment;

public interface InvestUpdater {

  void update(float newValue);

  void setSize(float newValue);

  float getSize();

  void setLastValue(float newValue);

  float getLastValue();

}

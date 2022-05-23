package net.snatchlab.investmentfarm.investment;

public class Currency {

  public final String name;
  public final String figi;
  public final String ticker;
  public final long lots;
  public final long units;

  public Currency(String name, String figi, String ticker, long lots, long units) {
    this.name = name;
    this.figi = figi;
    this.ticker = ticker;
    this.lots = lots;
    this.units = units;
  }
}

package net.snatchlab.investmentfarm.investment;

public class Money {

  public final long units;
  public final long nanos;

  public Money(long units, long nanos) {
    this.units = units;
    this.nanos = nanos;
  }
}

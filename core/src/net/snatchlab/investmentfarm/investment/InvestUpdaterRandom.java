package net.snatchlab.investmentfarm.investment;

import static com.badlogic.gdx.utils.NumberUtils.floatToIntBits;
import static com.badlogic.gdx.utils.NumberUtils.intBitsToFloat;

import com.badlogic.gdx.math.MathUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class InvestUpdaterRandom implements InvestUpdater {

  public AtomicInteger size = new AtomicInteger(0);
  private float originalValue = 0;
  public AtomicInteger lastValue = new AtomicInteger(0);

  public InvestUpdaterRandom() {
    originalValue = 50f;
    Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r,"sizer");
      t.setDaemon(true);
      return t;
    }).scheduleAtFixedRate(() -> {
      float random = MathUtils.random(1f, 100f);
      update(random);
    }, 0, 5, TimeUnit.SECONDS);
  }

  public void setSize(float newValue) {
    size.set(floatToIntBits(newValue));
  }

  public float getSize() {
    return intBitsToFloat(size.get());
  }

  public void setLastValue(float newValue) {
    lastValue.set(floatToIntBits(newValue));
  }

  public float getLastValue() {
    return intBitsToFloat(lastValue.get());
  }

  public void update(float newValue) {
    if (Float.compare(newValue, getLastValue()) != 0) {
      float newSize = newValue / originalValue;
      System.out.println("newSize = " + newSize);
      setSize(newSize);
      setLastValue(newValue);
    }
  }
}

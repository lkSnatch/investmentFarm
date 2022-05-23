package net.snatchlab.investmentfarm.investment;

import static com.badlogic.gdx.utils.NumberUtils.floatToIntBits;
import static com.badlogic.gdx.utils.NumberUtils.intBitsToFloat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class InvestUpdaterRandom implements InvestUpdater {

  private static final String TAG = InvestUpdaterRandom.class.getSimpleName();

  private final AtomicInteger size = new AtomicInteger(0);
  private final AtomicInteger originalValue = new AtomicInteger(0);
  private final AtomicInteger lastValue = new AtomicInteger(0);
  private final Currency currency;

  public InvestUpdaterRandom(Currency currency) {
    this.currency = currency;
    setOriginalValue(50f);
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

  public void setOriginalValue(float newValue) {
    originalValue.set(floatToIntBits(newValue));
  }

  public float getOriginalValue() {
    return intBitsToFloat(originalValue.get());
  }

  public void update(float newValue) {
    if (Float.compare(newValue, getLastValue()) != 0) {
      float newSize = newValue / getOriginalValue();
      Gdx.app.log(TAG, "newSize = " + newSize);
      setSize(newSize);
      setLastValue(newValue);
    }
  }

  public Currency getCurrency() {
    return currency;
  }
}

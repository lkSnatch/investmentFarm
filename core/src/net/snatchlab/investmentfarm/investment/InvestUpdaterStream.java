package net.snatchlab.investmentfarm.investment;

import static com.badlogic.gdx.utils.NumberUtils.floatToIntBits;
import static com.badlogic.gdx.utils.NumberUtils.intBitsToFloat;

import com.badlogic.gdx.math.MathUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.stream.MarketDataSubscriptionService;

public class InvestUpdaterStream implements InvestUpdater {

  public AtomicInteger size = new AtomicInteger(0);
  private float originalValue = 0;
  public AtomicInteger lastValue = new AtomicInteger(0);

  public InvestUpdaterStream(InvestApi investApi, List<Currency> figis) {
    originalValue = 50f;
    Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r,"sizer");
      t.setDaemon(true);
      return t;
    }).scheduleAtFixedRate(() -> {
      float random = MathUtils.random(1f, 100f);
      update(random);
    }, 0, 5, TimeUnit.SECONDS);

    subscribeOnPrice(figis, investApi);
  }

  private MarketDataSubscriptionService subscribeOnPrice(List<Currency> figis, InvestApi investApi) {
    MarketDataSubscriptionService dataStream = investApi.getMarketDataStreamService().newStream("stream id", response -> {
      if (response.hasLastPrice()) {
        /*
        response.getLastPrice() = figi: "BBG0013HGFT4"
        price {
          units: 57
          nano: 807500000
        }
        time {
          seconds: 1653320414
          nanos: 758018000
        }
         */
        LastPrice lastPrice = response.getLastPrice();
        long units = lastPrice.getPrice().getUnits();
        int nano = lastPrice.getPrice().getNano();
        update(Float.parseFloat(units + "." + nano));
        //lastPrice.getPrice().getUnits()
      }
    }, throwable -> {
      System.out.println("opapa error in the stream");
      throwable.printStackTrace();
    });
    System.out.println("dataStream = " + dataStream);

    dataStream.subscribeLastPrices(figis.stream().map(currency -> currency.figi).toList());

    return dataStream;
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

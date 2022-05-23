package net.snatchlab.investmentfarm.investment;

import static com.badlogic.gdx.utils.NumberUtils.floatToIntBits;
import static com.badlogic.gdx.utils.NumberUtils.intBitsToFloat;

import com.badlogic.gdx.Gdx;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;
import ru.tinkoff.piapi.core.stream.MarketDataSubscriptionService;

public class InvestUpdaterStream implements InvestUpdater {

  private static final String TAG = InvestUpdaterStream.class.getSimpleName();

  private final AtomicInteger size = new AtomicInteger(0);
  private final AtomicInteger originalValue = new AtomicInteger(0);
  private final AtomicInteger lastValue = new AtomicInteger(0);
  private final Currency currency;

  public InvestUpdaterStream(InvestApi investApi, Currency currency) {
    this.currency = currency;
    subscribeOnOriginalValue(currency, investApi);
    subscribeOnPrice(currency, investApi);
  }

  private void subscribeOnOriginalValue(Currency currency, InvestApi investApi) {
    Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r,"sizer");
      t.setDaemon(true);
      return t;
    }).scheduleAtFixedRate(() -> {
      try {
        MarketDataService marketDataService = investApi.getMarketDataService();
        List<HistoricCandle> candles = marketDataService.getCandlesSync(currency.figi,
                                                                        Instant.now().minusSeconds(60),
                                                                        Instant.now(), CandleInterval.CANDLE_INTERVAL_1_MIN);
        double sum = candles.stream()
            .mapToDouble(candle -> quotationToDouble(candle.getOpen(), currency) + quotationToDouble(candle.getClose(), currency)
                                   + quotationToDouble(candle.getLow(), currency) + quotationToDouble(candle.getHigh(), currency))
            .sum();
        double average = sum / (candles.size() * 4);
        if (Double.isNaN(average)) {
          Gdx.app.log(TAG, "The average value is NaN. Candles size = " + candles.size());
          setOriginalValue(0.0f);
        } else {
          setOriginalValue((float) average);
        }
      } catch (Throwable t) {
        Gdx.app.log(TAG, "Something goes wrong during calculating average value", t);
      }
    }, 0, 10, TimeUnit.SECONDS);
  }

  private void subscribeOnPrice(Currency currency, InvestApi investApi) {
    MarketDataSubscriptionService dataStream = investApi.getMarketDataStreamService().newStream("stream id", response -> {
      if (response.hasLastPrice()) {
        LastPrice lastPrice = response.getLastPrice();
        float currentPrice = quotationToFloat(lastPrice.getPrice(), currency);
        update(currentPrice);
      }
    }, throwable -> {
      Gdx.app.log(TAG, "Something goes wrong during streaming data", throwable);
    });
    dataStream.subscribeLastPrices(List.of(currency.figi));
  }

  private float quotationToFloat(Quotation price, Currency currency) {
    long units = price.getUnits();
    int nano = price.getNano();
    return Float.parseFloat(units + "." + nano) / currency.units;
  }

  private double quotationToDouble(Quotation price, Currency currency) {
    long units = price.getUnits();
    int nano = price.getNano();
    return Double.parseDouble(units + "." + nano) / currency.units;
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
      float originalValue = getOriginalValue();
      float newSize;
      if (originalValue == 0.0f) {
        newSize = 1f;
      } else {
        float diff = newValue - originalValue;
        // each step in 0.1 is a 100%. It means if the diff is 0.2, then an actor will be scaled to 2x.
        newSize = diff / 0.1f;
        newSize = newSize > 0 ? newSize : 1 / -newSize;
        Gdx.app.log(TAG, "newSize = " + newSize);
      }
      setSize(newSize);
      setLastValue(newValue);
    }
  }

  public Currency getCurrency() {
    return currency;
  }
}

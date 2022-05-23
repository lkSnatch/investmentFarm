package net.snatchlab.investmentfarm.investment;

import java.util.List;
import java.util.UUID;

import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.OrderDirection;
import ru.tinkoff.piapi.contract.v1.OrderType;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.SandboxService;

public class InvestManager {

  public InvestUpdater cowUpdater;
  public InvestUpdater llamaUpdater;

  public InvestManager() {
    InvestApi investApi = new TinkoffService().getSandbox();
    // define currencies
    Currency rub = new Currency("rub", "FG0000000000", "", 1, 1);
    Currency usd = new Currency("usd", "BBG0013HGFT4", "USD000UTSTOM", 1000, 1);
    Currency eur = new Currency("eur", "BBG0013HJJ31", "EUR_RUB__TOM", 1000, 1);
    Currency jpy = new Currency("jpy", "BBG0013HQ524", "JPYRUB_TOM", 100000, 100);
    List<Currency> figis = List.of(usd, eur);

    initService(investApi, figis);
    cowUpdater = new InvestUpdaterStream(investApi, usd);
    llamaUpdater = new InvestUpdaterRandom(eur);
  }

  private void initService(InvestApi investApi, List<Currency> figis) {

    SandboxService sandboxService = investApi.getSandboxService();
    // clear existing accounts
    sandboxService.getAccountsSync().forEach(account -> sandboxService.closeAccount(account.getId()));
    // open new one
    String accountId = sandboxService.openAccountSync();
    // add money
    sandboxService.payInSync(accountId, MoneyValue.newBuilder().setUnits(30_000).setNano(0).setCurrency("rub").build());
    sandboxService.payInSync(accountId, MoneyValue.newBuilder().setUnits(100).setNano(0).setCurrency("usd").build());
    sandboxService.payInSync(accountId, MoneyValue.newBuilder().setUnits(100).setNano(0).setCurrency("eur").build());
    sandboxService.payInSync(accountId, MoneyValue.newBuilder().setUnits(10_000).setNano(0).setCurrency("jpy").build());

    // buy currencies
    for (Currency currency : figis) {
      sandboxService.postOrderSync(currency.figi, 100,
                                   Quotation.getDefaultInstance(), OrderDirection.ORDER_DIRECTION_BUY,
                                   accountId, OrderType.ORDER_TYPE_MARKET,
                                   UUID.randomUUID().toString());
    }
    //float price = postOrderResponse_jpy.getTotalOrderAmount().getUnits() / postOrderResponse_jpy.getLotsRequested() / 100_000.0f / 100f;
  }
}

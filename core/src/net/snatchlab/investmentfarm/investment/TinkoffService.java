package net.snatchlab.investmentfarm.investment;

import ru.tinkoff.piapi.core.InvestApi;

public class TinkoffService {

  public static final String APP_NAME = "lkSnatch";
  public static final String SANDBOX_TOKEN = "";

  private InvestApi sandbox;

  public TinkoffService() {
  }

  public InvestApi getSandbox() {
    if (sandbox == null) {
      sandbox = InvestApi.createSandbox(SANDBOX_TOKEN, APP_NAME);
    }
    return sandbox;
  }
}

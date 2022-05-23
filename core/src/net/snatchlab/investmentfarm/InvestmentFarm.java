package net.snatchlab.investmentfarm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import net.snatchlab.investmentfarm.investment.InvestManager;
import net.snatchlab.investmentfarm.screens.GameplayScreen;
import net.snatchlab.investmentfarm.screens.StartScreen;

public class InvestmentFarm extends Game {

  public enum Screens {MENU, GAME, END}

  private Screen menuScreen;
  public Screen gameScreen;
  private InvestManager investManager;

  @Override
  public void create() {
    investManager = new InvestManager();
    changeScreen(Screens.MENU);
  }

  public void changeScreen(Screens newScreen) {
    switch (newScreen) {
      case MENU:
        if (menuScreen == null) {
          menuScreen = new StartScreen(this);
        }
        this.setScreen(menuScreen);
        break;
      case GAME:
        if (gameScreen == null) {
          gameScreen = new GameplayScreen(this, investManager);
        }
        this.setScreen(gameScreen);
        break;
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    menuScreen.dispose();
    gameScreen.dispose();
  }
}

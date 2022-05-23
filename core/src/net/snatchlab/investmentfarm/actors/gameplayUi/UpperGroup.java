package net.snatchlab.investmentfarm.actors.gameplayUi;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.Align;

import net.snatchlab.investmentfarm.actors.Repositioned;

public class UpperGroup extends HorizontalGroup implements Repositioned {

  @Override
  public void reposition(int width, int height) {
    setPosition(10f, height - getHeight() - 40f);
  }
}

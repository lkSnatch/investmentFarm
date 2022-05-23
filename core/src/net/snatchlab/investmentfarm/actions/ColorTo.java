package net.snatchlab.investmentfarm.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import net.snatchlab.investmentfarm.investment.InvestUpdater;

public class ColorTo extends Action {

  private final InvestUpdater investUpdater;
  private RepeatAction red;
  private RepeatAction green;
  private boolean redBlinking;
  private boolean greenBlinking;

  public ColorTo(InvestUpdater investUpdater) {
    this.investUpdater = investUpdater;
  }

  @Override
  public boolean act(float delta) {
    float newSize = investUpdater.getSize();
    if (newSize >= 1.50) {
      if (!redBlinking) {
        redBlinking = true;
        red = ActionsUtil.red();
        getActor().addAction(red);
        if (greenBlinking) {
          green.finish();
          greenBlinking = false;
        }
      }
    } else if (newSize <= 0.5) {
      if (!greenBlinking) {
        greenBlinking = true;
        green = ActionsUtil.green();
        getActor().addAction(green);
        if (redBlinking) {
          red.finish();
          redBlinking = false;
        }
      }
    } else {
      if (redBlinking) {
        red.finish();
        redBlinking = false;
      }
      if (greenBlinking) {
        green.finish();
        greenBlinking = false;
      }
    }
    return false;
  }
}

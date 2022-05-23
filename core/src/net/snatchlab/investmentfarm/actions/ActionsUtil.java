package net.snatchlab.investmentfarm.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

public class ActionsUtil {

  public static final Color WHITE = new Color(1, 1, 1, 1);

  public static Action breathe() {
    float delay = MathUtils.random(0.01f, 1f);
    Action breathe = breathe(0.05f, 0.05f, 0.5f, Interpolation.smooth);
    return Actions.delay(delay, breathe);
  }

  public static Action breathe(float amountX, float amountY, float duration, Interpolation interpolation) {
    Action inhaleAction = Actions.scaleBy(-amountX, amountY, duration, interpolation);
    Action exhaleAction = Actions.scaleBy(amountX, -amountY, duration, interpolation);
    return Actions.forever(Actions.sequence(inhaleAction, exhaleAction));
//    return Actions.repeat(RepeatAction.FOREVER, Actions.sequence(inhaleAction, exhaleAction, exhaleAction, inhaleAction));
  }

  public static RepeatAction red() {
    return red(0.5f);
  }

  public static RepeatAction red(float duration) {
    return Actions.forever(Actions.sequence(Actions.color(Color.RED, 0.5f), Actions.color(Color.WHITE, duration)));
  }

  public static RepeatAction green() {
    return green(0.5f);
  }

  public static RepeatAction green(float duration) {
    return Actions.forever(Actions.sequence(Actions.color(Color.GREEN, 0.5f), Actions.color(Color.WHITE, duration)));
  }
}

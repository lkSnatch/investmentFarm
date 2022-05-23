package net.snatchlab.investmentfarm.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import net.snatchlab.investmentfarm.investment.InvestUpdater;

public class ScaleTo extends Action {

  private InvestUpdater investUpdater;

  public ScaleTo(InvestUpdater investUpdater) {
    this.investUpdater = investUpdater;
  }

  @Override
  public boolean act(float delta) {
    float newSize = investUpdater.getSize();
    if (getActor().getScaleX() != newSize || getActor().getScaleY() != newSize) {
      //setScale(newSize);
      getActor().addAction(Actions.scaleTo(newSize, newSize, 0.5f, Interpolation.smooth));
//      getActor().addAction(Actions.sizeTo(actor.getWidth()*newSize, actor.getHeight()*newSize, 0.5f, Interpolation.smooth));

      /*getActor().addAction(Actions.sequence(Actions.scaleTo(newSize, newSize, 0.5f, Interpolation.smooth),
                                            Actions.run(() -> {
                                              if (getActor() instanceof Llama l) {
                                                l.breathAgain();
                                              }
                                            })));*/
      /*System.out.println("getActor().getActions().size = " + getActor().getActions().size);
      Array<Action> actions = getActor().getActions();
      for (Action action : actions) {
        if (action instanceof RepeatAction) {
          action.restart();
        }
      }*/
    }
    return false;
  }
}

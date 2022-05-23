package net.snatchlab.investmentfarm.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import net.snatchlab.investmentfarm.investment.InvestUpdater;

public class CurrentValue extends Action {

  private InvestUpdater investUpdater;
  private Label label;
  private float lastValue;

  public CurrentValue(InvestUpdater investUpdater, Label label) {
    this.investUpdater = investUpdater;
    this.label = label;
  }

  @Override
  public boolean act(float delta) {
    float newValue = investUpdater.getLastValue();
    if (lastValue != newValue) {
      //System.out.println("!=");
      label.setText(Float.toString(newValue));
      lastValue = newValue;
    }
    return false;
  }
}

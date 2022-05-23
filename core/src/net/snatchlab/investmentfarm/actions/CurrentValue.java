package net.snatchlab.investmentfarm.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import net.snatchlab.investmentfarm.investment.InvestUpdater;

public class CurrentValue extends Action {

  private InvestUpdater investUpdater;
  private Label currentLabel;
  private Label averageLabel;
  private float lastCurrentValue;
  private float lastAverageValue;

  public CurrentValue(InvestUpdater investUpdater, Label currentLabel, Label averageLabel) {
    this.investUpdater = investUpdater;
    this.currentLabel = currentLabel;
    this.averageLabel = averageLabel;
  }

  @Override
  public boolean act(float delta) {
    float newValue = investUpdater.getLastValue();
    if (lastCurrentValue != newValue) {
      currentLabel.setText(Float.toString(newValue));
      lastCurrentValue = newValue;
    }
    newValue = investUpdater.getOriginalValue();
    if (lastAverageValue != newValue) {
      averageLabel.setText(Float.toString(newValue));
      lastAverageValue = newValue;
    }
    return false;
  }
}

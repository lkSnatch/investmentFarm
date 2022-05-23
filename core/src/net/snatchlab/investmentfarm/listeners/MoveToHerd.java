package net.snatchlab.investmentfarm.listeners;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import net.snatchlab.investmentfarm.actors.characters.Ufo;

public class MoveToHerd extends ClickListener {

  private Actor herd;
  private Ufo ufo;
  private Image laser;
  private Actor flyVersion;
  private Image controlUp;
  private Image controlDown;
  private Array<Actor> actorsForHiding;


  public MoveToHerd(Actor herd, Ufo ufo, Image laser, Actor flyVersion, Image controlUp, Image controlDown, Array<Actor> actorsForHiding) {
    this.herd = herd;
    this.ufo = ufo;
    this.laser = laser;
    this.flyVersion = flyVersion;
    this.controlUp = controlUp;
    this.controlDown = controlDown;
    this.actorsForHiding = actorsForHiding;

    //controlUp.addAction(Actions.alpha(0));
    controlUp.setVisible(false);
    controlUp.setColor(Color.LIME);
    controlUp.setSize(controlUp.getWidth()/2, controlUp.getHeight()/2);
    controlUp.addListener(new TakeIt(herd, ufo, laser, flyVersion));

    //controlDown.addAction(Actions.alpha(0));
    controlDown.setVisible(false);
    controlDown.setColor(Color.FIREBRICK);
    controlDown.setSize(controlDown.getWidth()/2, controlDown.getHeight()/2);
    controlDown.addListener(new DropIt(herd, ufo, laser, flyVersion));
  }

  @Override
  public void clicked(InputEvent event, float x, float y) {
    // hide all ufo controls
    for (Actor actor : actorsForHiding) {
      actor.setVisible(false);
    }
    // define the center of the herd
    float animalCenterX = herd.getX() + herd.getWidth() * 0.5f;
    float animalCenterY = herd.getY() + herd.getHeight() * 0.5f;
    // move ufo to the center above the animals
    float ufoX = animalCenterX - ufo.getWidth() * 0.5f;
    float ufoY = animalCenterY + laser.getHeight() + Ufo.positionAboveAnimals;
    ufo.addAction(Actions.moveTo(ufoX, ufoY, 1, Interpolation.smooth));
    // set controls
    controlUp.setPosition(ufoX + ufo.getWidth(), ufoY + ufo.getHeight() * 0.5f);
    controlDown.setPosition(ufoX + ufo.getWidth(), ufoY + ufo.getHeight() * 0.5f - controlDown.getHeight());
    controlUp.addAction(Actions.delay(1, Actions.visible(true)));
    controlDown.addAction(Actions.delay(1, Actions.visible(true)));
  }
}

package net.snatchlab.investmentfarm.listeners;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import net.snatchlab.investmentfarm.actors.characters.Ufo;

public class DropIt extends ClickListener {

  private Actor herd;
  private Ufo ufo;
  private Image laser;
  private Actor flyVersion;

  public DropIt(Actor herd, Ufo ufo, Image laser, Actor flyVersion) {
    this.herd = herd;
    this.ufo = ufo;
    this.laser = laser;
    this.flyVersion = flyVersion;
  }

  @Override
  public void clicked(InputEvent event, float x, float y) {
    float animalCenterX = herd.getX() + herd.getWidth() * 0.5f;
    float animalCenterY = herd.getY() + herd.getHeight() * 0.5f;
    // move ufo to the center above the animals
    float ufoX = animalCenterX - ufo.getWidth() * 0.5f;
    float ufoY = animalCenterY + laser.getHeight() + Ufo.positionAboveAnimals;
    ufo.addAction(Actions.moveTo(ufoX, ufoY, 1, Interpolation.smooth));
    // set laser position to the center of animals
    laser.setPosition(animalCenterX - laser.getWidth() * 0.5f, animalCenterY);
    // fade in & out laser with delaying (ufo move time)
    laser.addAction(Actions.sequence(Actions.delay(1),
                                     Actions.fadeIn(2),
                                     Actions.fadeOut(2)));
    // fly animal should fade in while ufo moving, then move to ufo and disappear. Then return to the center of the animals
    flyVersion.addAction(Actions.sequence(Actions.moveToAligned(ufoX + ufo.getWidth() * 0.5f, ufoY, Align.center),
                                          Actions.delay(1),
                                          Actions.parallel(
                                              Actions.moveToAligned(animalCenterX, animalCenterY, Align.center, 4),
                                              Actions.fadeIn(0.2f),
                                              Actions.delay(3, Actions.fadeOut(1)),
                                              Actions.rotateBy(360, 4)
                                          )));
  }
}

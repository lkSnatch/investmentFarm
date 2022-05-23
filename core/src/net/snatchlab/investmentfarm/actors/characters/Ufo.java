package net.snatchlab.investmentfarm.actors.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import net.snatchlab.investmentfarm.actions.ActionsUtil;

public final class Ufo extends Image {

  public static final float positionAboveAnimals = 0f;

  public Ufo(TextureRegion region) {
    super(region);
    setName("ufo");
    //setSize(getWidth() / 8f, getHeight() / 8f);
    setY(100f);
    setOrigin(Align.center);
    addAction(ActionsUtil.breathe());
  }
}

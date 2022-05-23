package net.snatchlab.investmentfarm.actors.animals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import net.snatchlab.investmentfarm.actors.Repositioned;

public class Llama extends Image implements Repositioned {

  private static final float squareBeginX = 100;
  private static final float squareEndX = 300;
  private static final float squareBeginY = 250;
  private static final float squareEndY = 450;
  private AnimalType type;
  //private final Action breathe;

  public Llama(TextureRegion region, AnimalType type) {
    super(region);
    this.type = type;
    setName("llama");
    //setSize(getWidth() / 8f, getHeight() / 8f);
    reposition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    setOrigin(Align.center);
    /*breathe = ActionsUtil.breathe();
    addAction(breathe);*/
  }

  @Override
  public void act(float delta) {
    super.act(delta);
//    System.out.println("has breath: "+getActions().contains(breathe, true));
  }

  public void breathAgain() {
    //breathe.restart();
  }

  @Override
  public void reposition(int width, int height) {
    float x1 = width - squareEndX;
    float x2 = width - squareBeginX;
    float y1 = height - squareEndY;
    float y2 = height - squareBeginY;
    switch (type) {
      case NORMAL -> {
        // set it randomly inside the square
        setX(MathUtils.random(x1, x2 - getWidth()));
        setY(MathUtils.random(y1, y2 - getHeight()));
      }
      case FAT -> {
        // stretch it up to fill the square
        setX(x1);
        setY(y1);
        setWidth(x2 - x1);
        setHeight(y2 - y1);
      }
      case FLY -> {
        // place it into the center of the square
        setX(x1 + (x2 - x1) * 0.5f, Align.center);
        setY(y1 + (y2 - y1) * 0.5f, Align.center);
      }
    }
  }
}

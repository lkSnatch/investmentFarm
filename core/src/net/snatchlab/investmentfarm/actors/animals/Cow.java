package net.snatchlab.investmentfarm.actors.animals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import net.snatchlab.investmentfarm.actors.Repositioned;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class Cow extends Image implements Repositioned {

  private static final float squareBeginX = 100;
  private static final float squareEndX = 300;
  private static final float squareBeginY = 50;
  private static final float squareEndY = 250;
  private AnimalType type;
  private RandomGenerator randomGenerator;

  public Cow(TextureRegion region, AnimalType type) {
    super(region);
    this.type = type;
    randomGenerator = RandomGeneratorFactory.getDefault().create();
    setName("cow");
    //setSize(getWidth() / 8f, getHeight() / 8f);
    setOrigin(Align.center);
    reposition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    //addAction(ActionsUtil.breathe());
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void reposition(int width, int height) {
    float x1 = squareBeginX;
    float x2 = squareEndX;
    float y1 = squareBeginY;
    float y2 = squareEndY;
    switch (type) {
      case NORMAL -> {
        // set it randomly inside the square
        /*setX(MathUtils.random(x1, x2));
        setY(MathUtils.random(y1, y2));*/
        setX(randomGenerator.nextFloat(x1, x2 - getWidth()));
        setY(randomGenerator.nextFloat(y1, y2 - getHeight()));
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

package net.snatchlab.investmentfarm.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class ControlsAsset {

  public final AtlasRegion up;
  public final AtlasRegion down;

  public ControlsAsset(TextureAtlas atlas) {

    up = atlas.findRegion("shadedLight26");
    down = atlas.findRegion("shadedLight27");

  }
}

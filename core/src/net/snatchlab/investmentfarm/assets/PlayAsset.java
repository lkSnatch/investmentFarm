package net.snatchlab.investmentfarm.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class PlayAsset {

  public final AtlasRegion cow;
  public final AtlasRegion laser;
  public final AtlasRegion laserGround;
  public final AtlasRegion llama;
  public final AtlasRegion ufo;

  public PlayAsset(TextureAtlas atlas) {

    cow = atlas.findRegion("cow_one");
    laser = atlas.findRegion("laserGreen2");
    laserGround = atlas.findRegion("laserGreen_groundBurst");
    llama = atlas.findRegion("llama_one");
    ufo = atlas.findRegion("shipGreen_manned");

  }
}

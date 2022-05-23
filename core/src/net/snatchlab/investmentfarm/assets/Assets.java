package net.snatchlab.investmentfarm.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

  public static final String TAG = Assets.class.getName();

  public static final Assets instance = new Assets();

  public AssetManager assetManager;
  public PlayAsset playAsset;
  public ControlsAsset controlsAsset;

  public Skin skin;

  private Assets() {
  }

  public void init(AssetManager assetManager) {

    this.assetManager = assetManager;
    this.assetManager.setErrorListener(new AssetErrListener());

    // textures
    this.assetManager.load("packed/game/all.pack.atlas", TextureAtlas.class);
    this.assetManager.load("packed/controls/all.pack.atlas", TextureAtlas.class);

    // skin
    SkinLoader skinLoader = new SkinLoader(this.assetManager.getFileHandleResolver());
    this.assetManager.setLoader(Skin.class, skinLoader);
    this.assetManager.load("ui/uiskin.json", Skin.class);

    this.assetManager.finishLoading();

    playAsset = new PlayAsset(assetManager.get("packed/game/all.pack.atlas"));
    controlsAsset = new ControlsAsset(assetManager.get("packed/controls/all.pack.atlas"));

    skin = assetManager.get("ui/uiskin.json");
  }

  @Override
  public void dispose() {
    assetManager.dispose();
  }
}

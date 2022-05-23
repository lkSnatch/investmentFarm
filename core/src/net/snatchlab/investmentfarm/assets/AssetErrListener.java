package net.snatchlab.investmentfarm.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;

public class AssetErrListener implements AssetErrorListener {

  public static final String TAG = AssetErrListener.class.getName();

  @Override
  public void error(AssetDescriptor asset, Throwable throwable) {
    Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
  }
}

package net.snatchlab.investmentfarm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import net.snatchlab.investmentfarm.InvestmentFarm;
import net.snatchlab.investmentfarm.assets.Assets;

public class StartScreen implements Screen {

  private InvestmentFarm game;
  private Stage stage;
  private Skin skin;

  public StartScreen(InvestmentFarm game) {

    this.game = game;

    Assets.instance.init(new AssetManager());
    skin = Assets.instance.skin;
    stage = new Stage(new ScreenViewport());

    Stack stack = new Stack();
    stage.addActor(stack);
    stack.setFillParent(true);

    Table table = new Table(skin);
    stack.add(table);
    table.setFillParent(true);
    table.align(Align.center);

    table.row();
    Label titleStart = new Label("Farm money", skin, "font", "white");
    table.add(titleStart).padBottom(30f);

    table.row();
    Button gameButton = new TextButton("Start!", skin, "default");
    gameButton.addListener(new ChangeListener(){
      @Override
      public void changed (ChangeEvent event, Actor actor) {
        game.changeScreen(InvestmentFarm.Screens.GAME);
      }
    });
    gameButton.addAction(Actions.alpha(0f));
    gameButton.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(3), Actions.fadeOut(3))));
    table.add(gameButton).uniformX().padBottom(10f);

    table.row();
    Button exitButton = new TextButton("Exit", skin, "default");
    exitButton.addListener(new ChangeListener(){
      @Override
      public void changed (ChangeEvent event, Actor actor) {
        Gdx.app.exit();
      }
    });
    exitButton.addAction(Actions.alpha(0f));
    exitButton.addAction(Actions.delay(2f, Actions.forever(Actions.sequence(Actions.fadeIn(3), Actions.fadeOut(3)))));
    table.add(exitButton).uniformX().padBottom(10f);

  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void dispose() {
    stage.dispose();
    try {
      Assets.instance.dispose();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

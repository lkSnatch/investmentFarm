package net.snatchlab.investmentfarm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import net.snatchlab.investmentfarm.InvestmentFarm;
import net.snatchlab.investmentfarm.actions.ColorTo;
import net.snatchlab.investmentfarm.actions.CurrentValue;
import net.snatchlab.investmentfarm.actions.ScaleTo;
import net.snatchlab.investmentfarm.actors.animals.AnimalType;
import net.snatchlab.investmentfarm.actors.animals.Cow;
import net.snatchlab.investmentfarm.actors.animals.Llama;
import net.snatchlab.investmentfarm.actors.Repositioned;
import net.snatchlab.investmentfarm.actors.gameplayUi.UpperGroup;
import net.snatchlab.investmentfarm.assets.Assets;
import net.snatchlab.investmentfarm.actors.characters.Ufo;
import net.snatchlab.investmentfarm.investment.InvestManager;
import net.snatchlab.investmentfarm.listeners.MoveToHerd;

public class GameplayScreen implements Screen {

  private static final String TAG = GameplayScreen.class.getName();

  private InvestmentFarm game;
  private Stage stage;
  private Skin skin;
  private float spaceBetweenPlayers;
  private InvestManager investManager;
  private Array<Actor> actorsForRepositioning;
  private Array<Actor> actorsForHiding;
  private Label accountAmountLabel;
  private Label cowAmountLabel;
  private Label cowCurrentLabel;
  private Label llamaAmountLabel;
  private Label llamaCurrentLabel;

  public GameplayScreen(InvestmentFarm game, InvestManager investManager) {
    this.game = game;
    this.investManager = investManager;
    actorsForRepositioning = new Array<>();
    actorsForHiding = new Array<>();
    skin = Assets.instance.skin;
    stage = new Stage(new ScreenViewport());
    spaceBetweenPlayers = 50f;

    HorizontalGroup upperGroup = new UpperGroup();
    actorsForRepositioning.add(upperGroup);

    Image cowIcon = new Image(Assets.instance.playAsset.cow);
    upperGroup.addActor(cowIcon);

    Label cowAmountTextLabel = new Label("cows = ", skin, "window");
//    cowTextLabel.setFontScale(2f);
//    cowTextLabel.sizeBy(2f);
    cowAmountTextLabel.setColor(0f, 0.6f, 0f, 1f);
    cowAmountTextLabel.validate();
    upperGroup.addActor(cowAmountTextLabel);

    cowAmountLabel = new Label("23565360", skin, "window");
    cowAmountLabel.setColor(1f, 0.6f, 0f, 1f);
    upperGroup.addActor(cowAmountLabel);

    Label cowCurrentTextLabel = new Label(" , current = ", skin, "window");
    cowCurrentTextLabel.setColor(0f, 0.6f, 0f, 1f);
    cowCurrentTextLabel.validate();
    upperGroup.addActor(cowCurrentTextLabel);

    cowCurrentLabel = new Label("0", skin, "window");
    cowCurrentLabel.setColor(1f, 0.6f, 0f, 1f);
    cowCurrentLabel.addAction(new CurrentValue(investManager.cowUpdater, cowCurrentLabel));
    upperGroup.addActor(cowCurrentLabel);

    Image llamaIcon = new Image(Assets.instance.playAsset.llama);
    upperGroup.addActor(llamaIcon);

    Label llamaTextLabel = new Label("llamas = ", skin, "window");
    llamaTextLabel.setColor(0f, 0.6f, 0f, 1f);
    llamaTextLabel.validate();
    upperGroup.addActor(llamaTextLabel);

    llamaAmountLabel = new Label("23565360", skin, "window");
    llamaAmountLabel.setColor(1f, 0.6f, 0f, 1f);
    upperGroup.addActor(llamaAmountLabel);

    Label llamaCurrentTextLabel = new Label(" , current = ", skin, "window");
    llamaCurrentTextLabel.setColor(0f, 0.6f, 0f, 1f);
    llamaCurrentTextLabel.validate();
    upperGroup.addActor(llamaCurrentTextLabel);

    llamaCurrentLabel = new Label("0", skin, "window");
    llamaCurrentLabel.setColor(1f, 0.6f, 0f, 1f);
    llamaCurrentLabel.addAction(new CurrentValue(investManager.llamaUpdater, llamaCurrentLabel));
    upperGroup.addActor(llamaCurrentLabel);

    stage.addActor(upperGroup);

    Ufo ufo = new Ufo(Assets.instance.playAsset.ufo);
    //setUpCharacter(ufo);
    stage.addActor(ufo);
    Image laser = new Image(Assets.instance.playAsset.laser);
    laser.addAction(Actions.alpha(0));
    stage.addActor(laser);

    createCows(investManager, 10);

    Cow flyCow = new Cow(Assets.instance.playAsset.cow, AnimalType.FLY);
    flyCow.addAction(Actions.alpha(0));
    flyCow.addAction(new ScaleTo(investManager.cowUpdater));
    stage.addActor(flyCow);
    actorsForRepositioning.add(flyCow);

    Image controlUp = new Image(Assets.instance.controlsAsset.up);
    stage.addActor(controlUp);
    actorsForHiding.add(controlUp);
    Image controlDown = new Image(Assets.instance.controlsAsset.down);
    stage.addActor(controlDown);
    actorsForHiding.add(controlDown);
    Cow cow = new Cow(Assets.instance.playAsset.cow, AnimalType.FAT);
    cow.addAction(Actions.alpha(0));
    cow.addListener(new MoveToHerd(cow, ufo, laser, flyCow, controlUp, controlDown, actorsForHiding));
    stage.addActor(cow);
    actorsForRepositioning.add(cow);

    createLlamas(investManager, 10);

    Llama flyLlama = new Llama(Assets.instance.playAsset.llama, AnimalType.FLY);
    flyLlama.addAction(Actions.alpha(0));
    flyLlama.addAction(new ScaleTo(investManager.llamaUpdater));
    stage.addActor(flyLlama);
    actorsForRepositioning.add(flyLlama);

    controlUp = new Image(Assets.instance.controlsAsset.up);
    stage.addActor(controlUp);
    actorsForHiding.add(controlUp);
    controlDown = new Image(Assets.instance.controlsAsset.down);
    stage.addActor(controlDown);
    actorsForHiding.add(controlDown);
    Llama llama = new Llama(Assets.instance.playAsset.llama, AnimalType.FAT);
    llama.addAction(Actions.alpha(0));
    llama.addListener(new MoveToHerd(llama, ufo, laser, flyLlama, controlUp, controlDown, actorsForHiding));
    stage.addActor(llama);
    actorsForRepositioning.add(llama);

  }

  private void createCows(InvestManager investManager, int number) {
    for (int i = 0; i < number; i++) {
      Cow cow = new Cow(Assets.instance.playAsset.cow, AnimalType.NORMAL);
      cow.addAction(new ScaleTo(investManager.cowUpdater));
      cow.addAction(new ColorTo(investManager.cowUpdater));
      stage.addActor(cow);
      actorsForRepositioning.add(cow);
    }
  }

  private void createLlamas(InvestManager investManager, int number) {
    for (int i = 0; i < number; i++) {
      Llama llama = new Llama(Assets.instance.playAsset.llama, AnimalType.NORMAL);
      llama.addAction(new ScaleTo(investManager.llamaUpdater));
      llama.addAction(new ColorTo(investManager.llamaUpdater));
      stage.addActor(llama);
      actorsForRepositioning.add(llama);
    }
  }

  @Override
  public void show() {

    Gdx.input.setInputProcessor(stage);

  }

  @Override
  public void render(float delta) {

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.act(delta);
    stage.draw();

  }

  @Override
  public void resize(int width, int height) {

    stage.getViewport().update(width, height, true);

    for (Actor actor : actorsForRepositioning) {
      ((Repositioned) actor).reposition(width, height);
    }

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
    try {
      stage.dispose();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void gameOver() {
    game.changeScreen(InvestmentFarm.Screens.END);
  }
}

package in.lotusilab.fastbolt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.Fastbolt;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by rukmani on 4/20/17.
 */
public class SplashScreen implements Screen {
    private final Fastbolt gameApp;
    private Stage stage;

    private ShapeRenderer circle;
    private Image splashImage;

    public SplashScreen(Fastbolt gameApp){
        this.gameApp = gameApp;
        this.stage = new Stage(new FitViewport(InitValues.ASP_WIDTH, InitValues.ASP_HEIGHT, gameApp.camera));
        splashImage = new Image(new Texture(InitValues.LOGO_FILE));
        circle = new ShapeRenderer();
    }
    @Override
    public void show() {
        System.out.println("SPLASH");

        splashImage.setPosition(
                stage.getWidth() / 2 - splashImage.getWidth() / 2,
                stage.getHeight() + splashImage.getHeight());

        //splashImage.addAction(sequence(alpha(1), moveTo(stage.getWidth() / 2 - splashImage.getWidth() / 2, stage.getHeight() + splashImage.getHeight())));
        splashImage.addAction(
                sequence(
                        alpha(0.0f),
                        scaleTo(0.01f, 0.01f),
                        parallel(
                                fadeIn(2f, Interpolation.pow2),
                                scaleTo(1f, 1f, 2.5f, Interpolation.pow5),
                                moveTo(stage.getWidth() / 2 - splashImage.getWidth() / 2,
                                        stage.getHeight() / 2 - splashImage.getHeight() / 2,
                                        0.5f, Interpolation.elasticOut)),
                        delay(1.5f),
                        fadeOut(1.25f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                gameApp.setScreen(gameApp.gameScreen);
                            }
                        })));

        stage.addActor(splashImage);
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(InitValues.SPLASH_CLEAR_COLOR.r, InitValues.SPLASH_CLEAR_COLOR.g,
                InitValues.SPLASH_CLEAR_COLOR.b, InitValues.SPLASH_CLEAR_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

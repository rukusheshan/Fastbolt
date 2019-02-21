package in.lotusilab.fastbolt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.Fastbolt;

/**
 * Created by rukmani on 4/20/17.
 */
public class LoadingScreen implements Screen {
    private final Fastbolt gameApp;
    private Stage stage;
    private ShapeRenderer shapeRenderer;


    private float progress;

    public LoadingScreen(Fastbolt gameApp){
        this.gameApp = gameApp;
        this.stage = new Stage(new FitViewport(InitValues.ASP_WIDTH, InitValues.ASP_HEIGHT, gameApp.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    private void queueAssets(){
        gameApp.assetManager.load(InitValues.TEXTURE_ATLAS, TextureAtlas.class);
        //gameApp.assetManager.load(InitValues.BASIC_SKIN_ATLAS, TextureAtlas.class);
    }

    @Override
    public void show() {
        System.out.println("LOADING");
        progress = 0f;
        shapeRenderer.setAutoShapeType(true);
        queueAssets();
    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(InitValues.LOADING_CLEAR_COLOR.r, InitValues.LOADING_CLEAR_COLOR.g,
                InitValues.LOADING_CLEAR_COLOR.b, InitValues.LOADING_CLEAR_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameApp.spriteBatch.begin();
        gameApp.fontComingSoonBlack72.draw(gameApp.spriteBatch, "Loading",
                ((Gdx.graphics.getWidth() / 2) - (20 * 7)), (Gdx.graphics.getWidth() / 4));
        gameApp.spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(Gdx.graphics.getWidth() / 4, stage.getHeight() / 2 - stage.getHeight() / 4,
                Gdx.graphics.getWidth() / 2, 10);


        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(Gdx.graphics.getWidth() / 4, stage.getHeight() / 2 - stage.getHeight() / 4,
                (Gdx.graphics.getWidth() / 2) * progress, 10);
        shapeRenderer.end();

    }

    private void update() {
        progress = /*gameApp.assetManager.getProgress();//*/MathUtils.lerp(progress, gameApp.assetManager.getProgress(), 0.1f);
        if (gameApp.assetManager.update() && progress >= 1f - 0.00001f){
            gameApp.spriteAtlas = gameApp.assetManager.get(InitValues.TEXTURE_ATLAS, TextureAtlas.class);
            // TODO: change to splash screen
            gameApp.setScreen(gameApp.gameScreen);
        }
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
        shapeRenderer.dispose();
    }
}

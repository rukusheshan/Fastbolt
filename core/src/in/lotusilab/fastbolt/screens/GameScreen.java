package in.lotusilab.fastbolt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import in.lotusilab.fastbolt.Effects.Popup;
import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.Fastbolt;
import in.lotusilab.fastbolt.actors.Chitti;
import in.lotusilab.fastbolt.physics.CollisionListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
/**
 * Created by rukmani on 4/20/17.
 */
public class GameScreen implements Screen {
    private final Fastbolt gameApp;

    protected Integer numCrates, numMisses, numFired, numHit, numKilled;

    protected float scoreDispX, scoreDispY;

    protected Popup popup;

    private Chitti chitti;

    protected Image btnLeft, btnRight, btnJump, btnShoot, btnAttack;

    protected Stage stage;
    protected Group background, levelGroup, enemyGroup, playerGroup, weaponGroup, uiGroup;
    private World physicsWorld;


    public GameScreen(Fastbolt gameApp) {
        this.gameApp = gameApp;
        setStage();

        this.physicsWorld = new World(InitValues.GRAVITY, true);
        physicsWorld.setContactListener(new CollisionListener());

        numCrates = 0;
        scoreDispY = Gdx.graphics.getHeight() - 10;
        scoreDispX = Gdx.graphics.getWidth() / 2;
        scoreDispX -= 15;
    }

    private void setStage() {
        this.stage = new Stage(new FitViewport(896.0F, 512.0F, gameApp.camera));
        background = new Group();
        levelGroup = new Group();
        playerGroup = new Group();
        weaponGroup = new Group();
        enemyGroup = new Group();
        uiGroup = new Group();

        stage.addActor(background);
        stage.addActor(levelGroup);
        stage.addActor(enemyGroup);
        stage.addActor(playerGroup);
        stage.addActor(weaponGroup);
        stage.addActor(uiGroup);
    }

    public void setChitti(Chitti chitti) {
        this.chitti = chitti;
        chitti.setGameScreen(this);
    }

    public void crateCollected(){
        ++numCrates;
        scoreDispX = Gdx.graphics.getWidth() / 2;

        if (numCrates < 10)
            scoreDispX -= 15;
        else if (numCrates < 100)
            scoreDispX -= 30;
        else if (numCrates < 1000)
            scoreDispX -= 45;
        else if (numCrates < 10000)
            scoreDispX -= 60;
        else if (numCrates < 100000)
            scoreDispX -= 75;
        else if (numCrates < 1000000)
            scoreDispX -= 90;
        else if (numCrates < 10000000)
            scoreDispX -= 105;
    }

    public void showEffect(Vector2 position, InitValues.BalloonType type){
        popup.show(position, type);
    }

    public void shakeCamera(float shakeForce) {
        int r = MathUtils.random(1, 4);
        shakeForce /=  20.0f;
        stage.addAction(
                sequence(
                        moveBy(-shakeForce, 0, 0.02f),
                        moveBy(shakeForce, 0, 0.02f))
        );
        return;
        /*switch (r){
            case 1:
                stage.addAction(
                        sequence(
                                moveBy(shakeForce, shakeForce, 0.02f),
                                moveBy(-shakeForce, shakeForce, 0.02f),
                                moveBy(-shakeForce, -shakeForce, 0.02f),
                                moveBy(shakeForce, -shakeForce, 0.02f))
                );
                break;
            case 2:
                stage.addAction(
                        sequence(
                                moveBy(-shakeForce, shakeForce, 0.02f),
                                moveBy(-shakeForce, shakeForce, 0.02f),
                                moveBy(shakeForce, -shakeForce, 0.02f),
                                moveBy(shakeForce, -shakeForce, 0.02f))
                );
                break;
            case 3:
                stage.addAction(
                        sequence(
                                moveBy(-shakeForce, -shakeForce, 0.02f),
                                moveBy(shakeForce, shakeForce, 0.02f),
                                moveBy(-shakeForce, shakeForce, 0.02f),
                                moveBy(shakeForce, -shakeForce, 0.02f))
                );
                break;
            case 4:
                stage.addAction(
                        sequence(
                                moveBy(shakeForce, -shakeForce, 0.02f),
                                moveBy(-shakeForce, -shakeForce, 0.02f),
                                moveBy(shakeForce, shakeForce, 0.02f),
                                moveBy(-shakeForce, shakeForce, 0.02f))
                );
                break;
        }*/
    }

    @Override
    public void show() {
        System.out.println("GAME");
        gameApp.stageOrganizer.organizeStage(background, levelGroup, enemyGroup, playerGroup,
                weaponGroup, this.physicsWorld, this, InitValues.BEACH_LEVEL, gameApp.spriteAtlas);

        popup = new Popup(gameApp.spriteAtlas, stage);

        createUI();

        /*stage.addListener(new InputListener(){

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT)
                    chitti.startFire();
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT)
                    chitti.stopFire();
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode){
                    case Input.Keys.A:
                    case Input.Keys.LEFT:
                        chitti.runLeft = true;
                        chitti.runRight = false;
                        break;
                    case Input.Keys.D:
                    case Input.Keys.RIGHT:
                        chitti.runRight = true;
                        chitti.runLeft = false;
                        break;
                    case Input.Keys.W:
                    case Input.Keys.UP:
                        chitti.startJump();
                        break;
                    case Input.Keys.SPACE:
                        chitti.startFire();
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode){
                    case Input.Keys.A:
                    case Input.Keys.LEFT:
                        chitti.runLeft = false;
                        chitti.runRight = false;
                        break;
                    case Input.Keys.D:
                    case Input.Keys.RIGHT:
                        chitti.runRight = false;
                        chitti.runLeft = false;
                        break;
                    case Input.Keys.W:
                    case Input.Keys.UP:
                        chitti.endJump();
                        break;
                    case Input.Keys.SPACE:
                        chitti.stopFire();
                        break;
                }
                return true;
            }
        });*/

        Gdx.input.setInputProcessor(stage);
    }

    private void createUI(){
        btnLeft = createButton(InitValues.LEFT_BUTTON_DATA);
        btnLeft.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                chitti.runLeft = true;
                chitti.runRight = false;
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                chitti.runLeft = false;
                chitti.runRight = false;
            }
        });

        btnRight = createButton(InitValues.RIGHT_BUTTON_DATA);
        btnRight.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                chitti.runRight = true;
                chitti.runLeft = false;
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                chitti.runLeft = false;
                chitti.runRight = false;
            }
        });

        btnJump = createButton(InitValues.JUMP_BUTTON_DATA);
        btnJump.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                chitti.startJump();
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                chitti.endJump();
            }
        });

        btnShoot = createButton(InitValues.SHOOT_BUTTON_DATA);
        btnShoot.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                chitti.startFire();
                return true;
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                chitti.stopFire();
            }
        });

        btnAttack = createButton(InitValues.ATTACK_BUTTON_DATA);
    }

    private Image createButton(InitValues.ButtonData data){
        Image image = new Image(gameApp.spriteAtlas.findRegion(data.name));
        image.setPosition(data.posX, data.posY);
        image.setScale(data.scaleX, data.scaleY);
        image.addAction(alpha(InitValues.BUTTON_ALPHA));
        uiGroup.addActor(image);
        return image;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(InitValues.GAME_CLEAR_COLOR.r, InitValues.GAME_CLEAR_COLOR.g,
                InitValues.GAME_CLEAR_COLOR.b, InitValues.GAME_CLEAR_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        gameApp.spriteBatch.begin();
        gameApp.fontComingSoonGoldenRod60.draw(gameApp.spriteBatch, numCrates.toString(),
                scoreDispX, scoreDispY);
        gameApp.spriteBatch.end();

        //gameApp.box2DDebugRenderer.render(physicsWorld, gameApp.camera.combined);

    }

    private void update(float delta) {
        physicsWorld.step(0.2f, 8, 3);
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
        physicsWorld.dispose();
        stage.dispose();
    }
}

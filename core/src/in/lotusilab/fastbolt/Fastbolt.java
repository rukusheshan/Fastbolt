package in.lotusilab.fastbolt;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import in.lotusilab.fastbolt.screens.*;


public class Fastbolt extends Game {
	public SpriteBatch spriteBatch;
	public AssetManager assetManager;
	public OrthographicCamera camera;
	public TextureAtlas spriteAtlas;
	public StageOrganizer stageOrganizer;

	// screens
	public SplashScreen splashScreen;
	public GameScreen gameScreen;
	public LoadingScreen loadingScreen;

	//font
	public BitmapFont fontComingSoonBlack72, fontComingSoonGoldenRod60, fontComingSoonRed24;


	// old
	public Box2DDebugRenderer box2DDebugRenderer;

	@Override
	public void create () {
		assetManager = new AssetManager();
		stageOrganizer = new StageOrganizer();
		createViewport();
		createRenderItems();
		createPhysicsSystem();

		createScreens();

		this.setScreen(loadingScreen);

	}


	@Override
	public void render () {
		//physicsWorld.step(0.2f, 8, 3);
		super.render();

		//Gdx.gl.glClearColor(InitValues.CLEAR_COLOR.r, InitValues.CLEAR_COLOR.g, InitValues.CLEAR_COLOR.b, InitValues.CLEAR_COLOR.a);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//box2DDebugRenderer.render(physicsWorld, camera.combined);
	}


	@Override
	public void dispose () {
		spriteBatch.dispose();
		fontComingSoonBlack72.dispose();
		box2DDebugRenderer.dispose();

		splashScreen.dispose();
		gameScreen.dispose();
		loadingScreen.dispose();

	}

	private void createViewport(){

		float aspWidth = InitValues.ASP_WIDTH;
		float aspHeight = InitValues.ASP_HEIGHT;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, aspWidth, aspHeight);
		//camera = new OrthographicCamera(aspWidth, (aspHeight * (height / width)));

		//camera.position.z = InitValues.CAMERA_POS_Z;
		/*if (width > viewport.getScreenWidth())
			camera.position.x = (width - viewport.getScreenWidth()) / 2.0f;*/
		camera.update();
	}

	private void createRenderItems(){
		initFonts();
		spriteBatch = new SpriteBatch();
	}

	private void initFonts() {
		//Free type font generator

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(InitValues.COMING_SOON_FONT));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

		params.size = 72;
		params.color = Color.BLACK;

		fontComingSoonBlack72 = generator.generateFont(params);

		params.size = 60;
		params.color = Color.GOLDENROD;

		fontComingSoonGoldenRod60 = generator.generateFont(params);

		params.size = 5;
		params.color = Color.RED;

		fontComingSoonRed24 = generator.generateFont(params);
	}

	private void createScreens(){
		splashScreen = new SplashScreen(this);
		gameScreen = new GameScreen(this);
		loadingScreen = new LoadingScreen(this);
	}

	private void createPhysicsSystem() {
		box2DDebugRenderer = new Box2DDebugRenderer();
	}
}

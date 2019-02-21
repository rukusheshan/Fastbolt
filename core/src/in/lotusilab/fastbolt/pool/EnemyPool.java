package in.lotusilab.fastbolt.pool;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.actors.ActorState;
import in.lotusilab.fastbolt.actors.EnemyBot;
import in.lotusilab.fastbolt.screens.GameScreen;

/**
 * Created by rukmani on 4/7/17.
 */

public class EnemyPool {
    GameScreen gameScreen;
    World physicsWorld;
    Group group;
    TextureAtlas atlas;
    InitValues.EnemyData enemyData;

    ArrayList<InitValues.TextureSequenceData> textureSequenceData;

    private ArrayList<EnemyBot> enemyBots;

    public EnemyPool(GameScreen gameScreen, World physicsWorld, Group group, TextureAtlas atlas, InitValues.EnemyData enemyData) {
        this.gameScreen = gameScreen;
        this.physicsWorld = physicsWorld;
        this.group = group;
        this.atlas = atlas;
        this.enemyData = enemyData;

        textureSequenceData = new ArrayList<InitValues.TextureSequenceData>();
        textureSequenceData.add(enemyData.walkSeq);
        textureSequenceData.add(enemyData.jumpSeq);

        enemyBots = new ArrayList<EnemyBot>();
    }

    public EnemyBot send(){
        for (EnemyBot e : enemyBots)
            if (! e.isActive())
                return resetEnemy(e);
        return createEnemy();
    }

    private EnemyBot resetEnemy(EnemyBot e){
        e.activate(InitValues.ENEMY_SPAWN_POSITION.x, InitValues.ENEMY_SPAWN_POSITION.y,
                MathUtils.random(-1, 1), enemyData.health);
        return e;
    }

    private EnemyBot createEnemy(){
        ArrayList<ActorState> states = createRenderComponent();
        Body enemyPhysicsBody = createPhysicsBody();

        EnemyBot bot = new EnemyBot(states, enemyPhysicsBody, physicsWorld, enemyData.health, enemyData.velocity,
                InitValues.ENEMY_SPAWN_POSITION, MathUtils.random(-1, 1), gameScreen);

        enemyBots.add(bot);
        group.addActor(bot);

        return bot;
    }

    private ArrayList<ActorState> createRenderComponent(){
        ArrayList<ActorState> states = new ArrayList<ActorState>(textureSequenceData.size());
        for (InitValues.TextureSequenceData data : textureSequenceData) {
            states.add(createActorState(atlas, data));
        }
        return states;
    }

    private ActorState createActorState(TextureAtlas atlas,
                                        InitValues.TextureSequenceData data){
        Array<Image> images = new Array<Image>(data.seqNum);
        for (int i = 1; i < data.seqNum + 1; ++i) {
            Image image = new Image(atlas.findRegion(data.seqName + i));
            image.setScale(InitValues.SPRITE_SCALE);
            images.add(image);
        }
        return new ActorState(data.duration, images, Animation.PlayMode.LOOP, data.seqNum);
    }

    private Body createPhysicsBody(){
        InitValues.PhysicsBodyData data = enemyData.physicsBodyData;
        Vector2 position = InitValues.ENEMY_SPAWN_POSITION;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = data.getType();
        bodyDef.position.set(position.x, position.y);
        bodyDef.fixedRotation = true;

        Body physicsBody = physicsWorld.createBody(bodyDef);

        Shape shape;

        if (data.isCircle()) {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(data.getHalfWidth());
            shape = circleShape;
        }
        else {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(data.getHalfWidth(), data.getHalfHeight());
            shape = polygonShape;
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = data.getRestitution();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;

        if (data.isSensor())
            fixtureDef.isSensor = true;

        fixtureDef.filter.maskBits = data.getMask();
        fixtureDef.filter.categoryBits = data.getCategory();

        physicsBody.createFixture(fixtureDef);

        return physicsBody;
    }
}

package in.lotusilab.fastbolt.pool;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import in.lotusilab.fastbolt.actors.weapons.Bullet;

/**
 * Created by rukmani on 25/6/17.
 */

public class BulletPool {
    World physicsWorld;
    Group group;
    TextureAtlas atlas;
    InitValues.AmmoData ammoData;

    private ArrayList<Bullet> bullets;

    public BulletPool(World physicsWorld, Group group, TextureAtlas atlas,
                      InitValues.AmmoData ammoData) {
        this.physicsWorld = physicsWorld;
        this.group = group;
        this.atlas = atlas;
        this.ammoData = ammoData;

        bullets = new ArrayList<Bullet>();
    }

    public Bullet getBullet(){
        for (Bullet b : bullets)
            if (! b.isActive())
                return b;
        return createBullet();
    }

    private Bullet createBullet() {

        //System.out.println("created");

        Image bulletImage = createRenderComponent(ammoData.textureRegionData);
        Body bulletPhysicsBody = createPhysicsBody(ammoData.physicsBodyData, ammoData.offsetPosition);

        Vector2 force = new Vector2(ammoData.force);

        Bullet bullet = new Bullet(bulletImage, bulletPhysicsBody, InitValues.SPRITE_SCALE,
                force, ammoData.offsetPosition, ammoData.damage);

        bullets.add(bullet);
        group.addActor(bullet);

        return bullet;
    }

    private Image createRenderComponent(String textureRegionData){
        return new Image(atlas.findRegion(textureRegionData));
    }

    private ArrayList<ActorState> createRenderComponent(TextureAtlas atlas,
                                                        ArrayList<InitValues.TextureSequenceData> sequenceData){
        ArrayList<ActorState> states = new ArrayList<ActorState>(sequenceData.size());
        for (InitValues.TextureSequenceData data : sequenceData) {
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

    private Body createPhysicsBody(InitValues.PhysicsBodyData data, Vector2 position){
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
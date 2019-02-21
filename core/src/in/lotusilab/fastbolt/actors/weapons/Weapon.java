package in.lotusilab.fastbolt.actors.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.actors.GameCharacter;
import in.lotusilab.fastbolt.pool.BulletPool;

/**
 * Created by rukmani on 25-05-2018.
 */
public class Weapon extends GameCharacter {

    protected Vector2 playerPosition, offsetPosition;
    protected float recoilEffect, recoilForce;
    protected boolean playerFacingRight;
    protected boolean triggerReleased;
    protected boolean playerRecoil;

    protected World physicsWorld;
    protected Group group;

    protected TextureAtlas atlas;
    protected InitValues.AmmoData ammoData;

    protected BulletPool bulletPool;

    public Weapon(Image image, Body physicsBody, World physicsWorld,
                  Group group, TextureAtlas atlas,
                  Vector2 offsetPosition, float recoilEffect, float recoilForce, InitValues.AmmoData ammoData) {
        super(image, physicsBody, InitValues.SPRITE_SCALE);

        bulletPool = new BulletPool(physicsWorld, group, atlas, ammoData);

        this.physicsWorld = physicsWorld;
        physicsBody.setUserData(this);

        this.group = group;
        this.offsetPosition = offsetPosition;

        this.recoilEffect = recoilEffect;
        this.recoilForce = recoilForce;

        this.atlas = atlas;
        this.ammoData = ammoData;

        setActorType(InitValues.ActorType.WEAPON);
        playerFacingRight = true;

        playerRecoil = false;
        triggerReleased = true;
    }

    public float getRecoilForce(){return recoilForce;}
    public void recoilDone(){playerRecoil = false;}
    public boolean shouldRecoil(){return playerRecoil;}

    public boolean fire(){

        float yAccuracy = MathUtils.random(-0.2f, 0.6f);
        Bullet bullet;
        int isNeg = playerFacingRight? 1 : -1;
        try {
            bullet = bulletPool.getBullet();

            bullet.activate(playerPosition.x + isNeg * offsetPosition.x,
                    playerPosition.y + offsetPosition.y + yAccuracy, playerFacingRight);

            triggerReleased = false;

            playerRecoil = true;

            group.addActor(bullet);
        }catch (Exception e){
            //System.out.print(bullet.isActive());
            System.out.print(playerPosition.x);
        }
        return true;
    }

    public void setTriggerReleased(){
        triggerReleased = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        float x, y;

        if (playerFacingRight) {
            x = playerPosition.x +
                    offsetPosition.x;
        }
        else {
            x = playerPosition.x -
                    (offsetPosition.x +
                            InitValues.PLAYER_PHYSICS_DATA.getHalfWidth() *
                                    InitValues.SPRITE_SCALE);// + (activeImage.getImageWidth() * getScaleX()));
        }

        y = playerPosition.y +
                offsetPosition.y;

        physicsBody.setTransform(x, y, 0);

        activeImage.setPosition(x, y);

        activeImage.setScaleX(playerFacingRight?
                InitValues.SPRITE_SCALE:
                -InitValues.SPRITE_SCALE);

        super.act(delta);
    }

    public void setPlayerPosition(Vector2 playerPosition, boolean facingRight) {
        this.playerPosition = playerPosition;
        this.playerFacingRight = facingRight;
    }
}

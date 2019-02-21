package in.lotusilab.fastbolt.actors.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.actors.GameCharacter;

/**
 * Created by rukmani on 28/7/17.
 */

public class Ammo extends GameCharacter {
    protected Vector2 force, offset;

    protected float damage;

    protected boolean active, toSleep;

    public Ammo(Image image, Body physicsBody, float scale,
                  Vector2 force, Vector2 offset, float damage){
        super(image, physicsBody, scale);

        setActorType(InitValues.ActorType.AMMO);

        physicsBody.setGravityScale(0.0f);

        physicsBody.setUserData(this);
        physicsBody.setBullet(true);

        active = true;
        toSleep = false;

        this.offset = offset;
        this.force = force;

        this.damage = damage;

    }

    public float getDamage(){return damage;}

    public boolean isActive(){return active;}

    public void activate(float x, float y, boolean facingRight) {
        active = true;

        physicsBody.setAwake(true);

        physicsBody.setTransform(x, y, 0.0f);
        activeImage.setPosition(x, y);
    }

    protected void deactivate(){
        toSleep = false;
        active = false;
        //System.out.println("deactivated");
        removeActor(this);
        physicsBody.setTransform(-700.0f, 900.0f, 0.0f);
        physicsBody.setAwake(false);
    }

    @Override
    public void startContact(GameCharacter other){

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

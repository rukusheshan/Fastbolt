package in.lotusilab.fastbolt.actors.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.actors.GameCharacter;

/**
 * Created by rukmani on 21-06-2018.
 */
public class Bullet extends Ammo {

    protected Vector2 force, offset;

    protected float damage;

    protected boolean active, toSleep;

    public Bullet(Image image, Body physicsBody, float scale,
                  Vector2 force, Vector2 offset, float damage){
        super(image, physicsBody, scale, force, offset, damage);

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

    @Override
    public void activate(float x, float y, boolean facingRight) {

        super.activate(x, y, facingRight);

        physicsBody.setAwake(true);
        if (facingRight) {
            x += offset.x;
            if (force.x < 0)
                force.x *= -1;
        }
        else {
            x -= offset.x;
            if (force.x > 0)
                force.x *= -1;
        }
        y += offset.y;

        force.y = y * offset.y * 0.08f * MathUtils.randomSign();
        physicsBody.setLinearVelocity(force);//, physicsBody.getWorldCenter(), true);
    }

    @Override
    public void startContact(GameCharacter other){
        if (other.actorType == InitValues.ActorType.ENEMY
                || other.actorType == InitValues.ActorType.BRICK
                || other.actorType == InitValues.ActorType.PLATFORM){
            toSleep = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!active) return;
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {

        if (toSleep)
            deactivate();

        if (!active) return;

        if (force.x < 0)
            activeImage.setScale(scale*-1, scale);
        else
            activeImage.setScale(scale);

        activeImage.setPosition(physicsBody.getPosition().x, physicsBody.getPosition().y);
        super.act(delta);
    }

}



package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.actors.weapons.Bullet;
import in.lotusilab.fastbolt.screens.GameScreen;

/**
 * Created by rukmani on 4/7/17.
 */

public class EnemyBot extends GameCharacter {

    protected boolean facingRight, active, toSleep, toJump, toDie;
    protected int directionFlag;
    protected Vector2 currVelocity;
    protected float speed;
    protected float health;
    protected float xAdjust;

    protected GameScreen screen;

    public EnemyBot(ArrayList<ActorState> actorStates, Body physicsBody, World physicsWorld,
                    float health, float speed,
                    Vector2 spawnPosition, int directionFlag, GameScreen screen){

        super(actorStates, physicsBody, physicsWorld, InitValues.SPRITE_SCALE);
        setZIndex(InitValues.GAMEELEMENTS_Z_INDEX);

        setActorType(InitValues.ActorType.ENEMY);

        physicsBody.setUserData(this);

        this.speed = speed;
        currVelocity = new Vector2();

        this.screen = screen;

        activate(spawnPosition.x, spawnPosition.y, directionFlag, health);
    }

    public boolean isActive(){return active;}

    public void activate(float x, float y, int directionFlag, float health){
        active = true;
        toJump = false;
        toDie = false;

        activeState = states.get(InitValues.ENEMY_JUMP_STATE_INDEX);

        physicsBody.setTransform(x, y, 0);
        physicsBody.setGravityScale(0.5f);

        this.directionFlag  = directionFlag;
        if (directionFlag == 1) {
            facingRight = true;
            currVelocity.x = speed;
        }
        else {
            facingRight = false;
            currVelocity.x = speed * -1;
        }

        this.health = health;
    }

    protected void deactivate(){
        toSleep = false;
        active = false;
        toJump = false;

        //System.out.println("deactivated");
        physicsBody.setGravityScale(0.0f);
        removeActor(this);
        physicsBody.setTransform(700.0f, 900.0f, 0.0f);
        physicsBody.setAwake(false);
    }

    @Override
    public void startContact(GameCharacter other){
        switch (other.actorType){
            case BRICK:
                changeDirection();
                break;
            case AMMO:
                getHit((Bullet) other);
                break;
            case TRIGGER:
                toJump = true;
                break;
            case EXIT_PORTAL:
                toSleep = true;
                break;
        }
    }

    private void jump() {

        //physicsBody.applyForceToCenter(0, 10, true);
        /*physicsBody.applyLinearImpulse(0, 20,
                physicsBody.getPosition().x, physicsBody.getPosition().y, true);*/
    }

    private void getHit(Bullet other) {
        Bullet b = other;
        health -= b.getDamage();
        if (health <= 0) {
            toDie = true;
            screen.showEffect(
                    new Vector2(physicsBody.getPosition().x, physicsBody.getPosition().y),
                    InitValues.BalloonType.KILL);
        }
    }

    private void changeDirection() {
        if (facingRight) {
            currVelocity.x = speed * -1;
            facingRight = false;
        }
        else {
            currVelocity.x = speed;
            facingRight = true;
        }
    }

    protected void die(){
        //show death animation and then this
        //System.out.println("Killed");
        active = false;
        toDie = false;
        removeActor(this);
        physicsBody.setTransform(700.0f, 900.0f, 0.0f);
        physicsBody.setAwake(false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!active) return;
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {

        if (toSleep) deactivate();

        if (!active) return;

        if (toDie) die();

        if (toJump) jump();

        onGroundTest();
        if (onGround) {
            gotoState(InitValues.ENEMY_WALK_STATE_INDEX);
        }
        else {
            gotoState(InitValues.ENEMY_JUMP_STATE_INDEX);
        }

        super.act(delta);

        physicsBody.setLinearVelocity(currVelocity);//, physicsBody.getWorldCenter(), true);

        if (facingRight) {
            activeImage.setScale(scale);
            xAdjust = - (halfWidth * getScaleX());
        }
        else {
            activeImage.setScale(scale * -1, scale);
            xAdjust = (halfWidth * getScaleX());
        }

        activeImage.setPosition(physicsBody.getPosition().x + xAdjust,
                physicsBody.getPosition().y - (halfHeight * getScaleY()));
    }
}

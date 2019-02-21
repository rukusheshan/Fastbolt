package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.actors.weapons.Weapon;
import in.lotusilab.fastbolt.screens.GameScreen;

/**
 * Created by rukmani on 29-04-2018.
 */
public class Chitti extends GameCharacter {

    private Vector2 currLinearVelocity, currPosition;
    public boolean facingRight;

    private GameScreen gameScreen;

    private Weapon weapon;
    public boolean fire;

    public boolean runRight, runLeft, jump, doubleJumping;
    private Vector2 recoilForce;

    private int jumpCount, maxJumpStep;

    public Chitti(ArrayList<ActorState> states, final Body physicsBody,
                  World physicsWorld, Weapon weapon){

        super(states, physicsBody, physicsWorld, InitValues.SPRITE_SCALE);
        setZIndex(InitValues.GAMEELEMENTS_Z_INDEX);

        this.actorType = InitValues.ActorType.PLAYER;

        recoilForce = new Vector2(0.0f, 0.0f);
        setWeapon(weapon);

        physicsBody.setUserData(this);
        resetParams();
    }

    private void resetParams() {

        super.resetRays();

        currLinearVelocity = new Vector2(0, 0);
        currPosition = new Vector2(0, 0);

        facingRight = true;

        maxJumpStep = InitValues.PLAYER_MAX_JUMP_COUNT;
        jumpCount = 0;

        runLeft = runRight = jump = doubleJumping = false;
        fire = false;
    }

    public void setGameScreen(GameScreen gameScreen){this.gameScreen = gameScreen;}

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    private void updateMovement(){

        if (runLeft) runLeft();
        else if (runRight) runRight();
        else stop();

        if (jump) jump();

        onGroundTest();

        updateState();

        if (fire)
            if (weapon.fire())
                gameScreen.shakeCamera(weapon.getRecoilForce());

        if (weapon.shouldRecoil()) applyRecoil();
    }

    private void updateState() {
        if (onGround) {
            if (runLeft | runRight) gotoState(InitValues.PLAYER_WALK_STATE_INDEX);
            else gotoState(InitValues.PLAYER_IDLE_STATE_INDEX);
        }
        else {
            gotoState(InitValues.PLAYER_JUMP_STATE_INDEX);
        }
    }

    private void applyRecoil(){
        int isNeg = facingRight?-1:1;
        recoilForce.x = weapon.getRecoilForce() * isNeg;
        physicsBody.applyLinearImpulse(recoilForce, physicsBody.getPosition(), true);
        weapon.recoilDone();
    }

    @Override
    public void startContact(GameCharacter other){
        if (other.actorType == InitValues.ActorType.ENEMY
                || other.actorType == InitValues.ActorType.ENTRY_PORTAL
                || other.actorType == InitValues.ActorType.EXIT_PORTAL){
            //you die
        }

        if (other.actorType == InitValues.ActorType.CRATE) {
            Crate c = (Crate)other;
            weapon = c.getWeapon();
            weapon.setPlayerPosition(currPosition, facingRight);
            gameScreen.crateCollected();
        }
    }

    @Override
    public void endContact(GameCharacter other){

    }

    public void startFire(){
        fire = true;
    }

    public void stopFire(){
        fire = false;
        weapon.setTriggerReleased();
    }

    public void jump(){
        if (jumpCount < maxJumpStep){
            ++jumpCount;

            currLinearVelocity = physicsBody.getLinearVelocity();

            currLinearVelocity.y += InitValues.PLAYER_MOVEMENT_JUMP_SPEED * Gdx.graphics.getDeltaTime();

            physicsBody.setLinearVelocity(currLinearVelocity);
        }
        if (runLeft) flyLeft();
        else if (runRight) flyRight();
    }

    public void startJump(){
        if (onGround) {
            doubleJumping = false;
            jump = true;
        }
        else if (!doubleJumping){
            doubleJumping = true;
            jump = true;
            jumpCount = 0;
        }
    }

    public void endJump(){
        jumpCount = 0;
        jump = false;
    }

    private void flyLeft(){
        setScale(-1.0f, 1.0f);
        facingRight = false;

        currLinearVelocity = physicsBody.getLinearVelocity();

        if (currLinearVelocity.x >= InitValues.PLAYER_LEFT_ON_AIR_SPEED)
            currLinearVelocity.x += InitValues.PLAYER_LEFT_ON_AIR_SPEED * Gdx.graphics.getDeltaTime();
        /*if (!onGround)
            currLinearVelocity.x += InitValues.PLAYER_LEFT_SPEED * Gdx.graphics.getDeltaTime();*/

        physicsBody.setLinearVelocity(currLinearVelocity.x, currLinearVelocity.y);
    }

    private void flyRight(){
        setScale(1.0f, 1.0f);
        facingRight = true;

        currLinearVelocity = physicsBody.getLinearVelocity();
        if (currLinearVelocity.x <= InitValues.PLAYER_RIGHT_ON_AIR_SPEED)
            currLinearVelocity.x += InitValues.PLAYER_RIGHT_ON_AIR_SPEED * Gdx.graphics.getDeltaTime();
        /*if (!onGround)
            currLinearVelocity.x += InitValues.PLAYER_RIGHT_SPEED * Gdx.graphics.getDeltaTime();*/

        physicsBody.setLinearVelocity(currLinearVelocity.x, currLinearVelocity.y);
    }

    public void runLeft(){
        setScale(-1.0f, 1.0f);
        facingRight = false;

        currLinearVelocity = physicsBody.getLinearVelocity();

        if (currLinearVelocity.x >= InitValues.PLAYER_LEFT_SPEED)
            currLinearVelocity.x += InitValues.PLAYER_LEFT_SPEED * Gdx.graphics.getDeltaTime();
        /*if (!onGround)
            currLinearVelocity.x += InitValues.PLAYER_LEFT_SPEED * Gdx.graphics.getDeltaTime();*/

        physicsBody.setLinearVelocity(currLinearVelocity.x, currLinearVelocity.y);
    }

    public void runRight(){
        setScale(1.0f, 1.0f);
        facingRight = true;

        currLinearVelocity = physicsBody.getLinearVelocity();
        if (currLinearVelocity.x <= InitValues.PLAYER_RIGHT_SPEED)
            currLinearVelocity.x += InitValues.PLAYER_RIGHT_SPEED * Gdx.graphics.getDeltaTime();
        /*if (!onGround)
            currLinearVelocity.x += InitValues.PLAYER_RIGHT_SPEED * Gdx.graphics.getDeltaTime();*/

        physicsBody.setLinearVelocity(currLinearVelocity.x, currLinearVelocity.y);
    }

    private void stop(){
        currLinearVelocity = physicsBody.getLinearVelocity();
        physicsBody.setLinearVelocity(0.0f, currLinearVelocity.y);
    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x, y);
        physicsBody.setTransform(new Vector2(x, y), 0.0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        activeImage.setScale(getScaleX() * scale, getScaleY() * scale);
        super.draw(batch, parentAlpha);
        weapon.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateMovement();
        currPosition.set(physicsBody.getPosition().x - (halfWidth * getScaleX()),
                physicsBody.getPosition().y - halfHeight);

        weapon.setPlayerPosition(currPosition, facingRight);
        activeImage.setPosition(currPosition.x, currPosition.y);
        weapon.act(delta);
    }
}

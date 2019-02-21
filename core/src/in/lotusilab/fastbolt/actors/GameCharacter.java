package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

import in.lotusilab.fastbolt.InitValues;

/**
 * Created by rukmani on 29-04-2018.
 */
public class GameCharacter extends Group{

    protected Body physicsBody;
    protected ArrayList<ActorState> states;
    protected ActorState activeState;
    protected ActorState nextState;

    protected Image activeImage;
    protected float halfWidth, halfHeight;
    protected float scale;

    protected boolean onGround;

    private World physicsWorldRef;

    protected Vector2 downRayOrigin, downRayEnd;

    public InitValues.ActorType actorType;

    public GameCharacter(Body physicsBody){
        this.physicsBody = physicsBody;
    }

    public GameCharacter(Image image, Body physicsBody, float scale){
        this.scale = scale;
        this.activeImage = image;
        nextState = activeState = null;
        initImageParams(physicsBody);
        resetRays();
    }

    public GameCharacter(ActorState state, Body physicsBody, float scale){
        this.scale = scale;
        nextState = activeState = state;
        activeImage = activeState.getKeyFrame(0);
        initImageParams(physicsBody);
        resetRays();
    }

    public GameCharacter(ArrayList<ActorState> states, Body physicsBody, float scale){
        this.scale = scale;
        this.states = states;
        nextState = activeState = this.states.get(0);
        activeImage = activeState.getKeyFrame(0);
        initImageParams(physicsBody);
        resetRays();
    }

    public GameCharacter(ArrayList<ActorState> states, Body physicsBody, World physicsWorld, float scale){
        this.scale = scale;
        this.states = states;
        this.physicsWorldRef = physicsWorld;
        nextState = activeState = this.states.get(0);
        activeImage = activeState.getKeyFrame(0);
        initImageParams(physicsBody);
        resetRays();
    }

    protected void resetRays(){
        // ray for checking collision with ground
        downRayOrigin = new Vector2(0, 0);
        downRayEnd = new Vector2(0, -1);
    }

    protected void onGroundTest() {
        onGround = groundRayTest();
    }

    private boolean groundRayTest(){
        //array because it should be accessed from inner class
        final boolean[] retVal = {false};
        downRayEnd.x = downRayOrigin.x = physicsBody.getPosition().x;
        downRayOrigin.y = physicsBody.getPosition().y;


        float deltaTime = Gdx.graphics.getDeltaTime();
        float minRayLen = (halfHeight * 3);
        float rayLength = (Math.abs(physicsBody.getLinearVelocity().y) * deltaTime) + deltaTime;

        rayLength = rayLength < minRayLen ? minRayLen : rayLength;

        downRayEnd.y = downRayOrigin.y - rayLength;

        physicsWorldRef.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

                GameCharacter userData = (GameCharacter)fixture.getBody().getUserData();

                if (userData.actorType == InitValues.ActorType.PLATFORM) {
                    retVal[0] = true;
                    return 0;
                }
                else{
                    return -1;
                }
            }
        }, downRayOrigin, downRayEnd);

        return retVal[0];
    }

    protected void gotoState(int stateIndex){
        if (states != null)
            if (stateIndex < states.size())
                nextState = states.get(stateIndex);
    }

    public void setActorType(InitValues.ActorType actorType) {
        this.actorType = actorType;
    }

    public void startContact(GameCharacter other){

    }

    public void endContact(GameCharacter other){

    }

    private void initImageParams(Body physicsBody) {
        halfWidth = activeImage.getWidth() / 20f;
        halfHeight = activeImage.getHeight() / 20f;

        this.physicsBody = physicsBody;

        float posX = this.physicsBody.getPosition().x - halfWidth;
        float posY = this.physicsBody.getPosition().y - halfHeight;

        if (states != null){
            for (ActorState actorState : states)
                processState(actorState);
        }

        else if (activeState != null){
            processState(activeState);
        }

        else {
            this.activeImage.setPosition(posX, posY);
            this.activeImage.setScale(scale);
        }
    }

    private void processState(ActorState state){
        float posX = this.physicsBody.getPosition().x - halfWidth;
        float posY = this.physicsBody.getPosition().y - halfHeight;

        if (posX < 0.00001f) posX = 0.0f;
        if (posY < 0.00001f) posY = 0.0f;

        Object[] frames = state.getKeyFrames();
        for (int i = 0; i < state.numFrames; ++i){
            Image image = (Image)frames[i];
            image.setPosition(posX, posY);
            image.setScale(scale);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        activeImage.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        updateState(delta);
        super.act(delta);
    }

    private void updateState(float delta) {
        if (activeState != null) {
            if (activeState != nextState) activeState = nextState;
            activeImage = activeState.getKeyFrame(delta);
        }
        activeImage.act(delta);
    }
}

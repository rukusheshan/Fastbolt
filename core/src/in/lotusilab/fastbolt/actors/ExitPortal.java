package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

import in.lotusilab.fastbolt.InitValues;

/**
 * Created by rukmani on 17-06-2018.
 */
public class ExitPortal extends GameCharacter {

    private Body playerGate;
    public ExitPortal(ActorState state, Body physicsBody, Body playerGate){
        super(state, physicsBody, InitValues.SPRITE_SCALE);
        this.playerGate = playerGate;

        setActorType(InitValues.ActorType.EXIT_PORTAL);

        playerGate.setUserData(this);
        physicsBody.setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void startContact(GameCharacter other){

    }

    @Override
    public void endContact(GameCharacter other){

    }
}

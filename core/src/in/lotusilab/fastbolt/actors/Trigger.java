package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

import in.lotusilab.fastbolt.InitValues;

/**
 * Created by rukmani on 14-07-2018.
 */

public class Trigger extends GameCharacter {

    public Trigger(Body physicsBody){
        super(physicsBody);
        physicsBody.setUserData(this);
        setActorType(InitValues.ActorType.TRIGGER);
    }

    @Override
    public void startContact(GameCharacter other){

    }

    @Override
    public void endContact(GameCharacter other){

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    @Override
    public void act(float delta) {

    }
}

package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.actors.weapons.Weapon;

/**
 * Created by rukmani on 25-05-2018.
 */
public class Crate extends GameCharacter {

    ArrayList<Vector2> spawnPositions;
    ArrayList<Weapon> weapons;
    private boolean shouldRespawn;

    public Crate(ActorState state, Body physicsBody, ArrayList<Weapon> weapons) {
        super(state, physicsBody, InitValues.SPRITE_SCALE);
        setZIndex(InitValues.GAMEELEMENTS_Z_INDEX);

        physicsBody.setUserData(this);

        shouldRespawn = true;

        setActorType(InitValues.ActorType.CRATE);

        spawnPositions = new ArrayList<Vector2>();
        this.weapons = weapons;
    }

    public void addSpawnPosition(Vector2 position){
        spawnPositions.add(position);
    }

    private void respawn(){
        int r = MathUtils.random(spawnPositions.size());
        physicsBody.setTransform(spawnPositions.get(r).x, spawnPositions.get(r).y, 0.0f);
        shouldRespawn = false;
    }

    @Override
    public void startContact(GameCharacter other) {
        super.startContact(other);
        if (other.actorType == InitValues.ActorType.PLAYER)
            shouldRespawn = true;
        if (other.actorType == InitValues.ActorType.BRICK) {
            physicsBody.setLinearVelocity(0.0f, 0.0f);
        }
    }

    @Override
    public void endContact(GameCharacter other) {
        super.endContact(other);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        if (shouldRespawn)
            respawn();

        super.act(delta);
        activeImage.setPosition(physicsBody.getPosition().x - halfWidth,
                physicsBody.getPosition().y - halfHeight);
    }

    public Weapon getWeapon(){return weapons.get(MathUtils.random(weapons.size()-1));}

    public Weapon getDefaultWeapon() {
        return weapons.get(0);
    }
}

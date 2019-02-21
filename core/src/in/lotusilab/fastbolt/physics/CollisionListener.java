package in.lotusilab.fastbolt.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import in.lotusilab.fastbolt.actors.GameCharacter;

/**
 * Created by rukmani on 10-05-2018.
 */
public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();

        GameCharacter userDataA = (GameCharacter) A.getBody().getUserData();
        GameCharacter userDataB = (GameCharacter) B.getBody().getUserData();

        userDataA.startContact(userDataB);
        userDataB.startContact(userDataA);

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

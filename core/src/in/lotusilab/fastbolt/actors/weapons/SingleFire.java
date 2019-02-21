package in.lotusilab.fastbolt.actors.weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import in.lotusilab.fastbolt.InitValues;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by rukmani on 25/6/17.
 */

public class SingleFire extends Weapon{

    public SingleFire(Image image, Body physicsBody, World physicsWorld,
                      Group group, TextureAtlas atlas,
                      Vector2 offsetPosition, float recoilEffect, float recoilForce,
                      InitValues.AmmoData ammoData){
        super(image, physicsBody, physicsWorld, group, atlas, offsetPosition,
                recoilEffect, recoilForce, ammoData);

        triggerReleased = true;
    }

    @Override
    public boolean fire(){
        if (triggerReleased) {
            return super.fire();

            /*int isNeg = playerFacingRight? 1 : -1;

            float curX = activeImage.getX();
            float curY = activeImage.getY();
            activeImage.addAction(
                    sequence(moveTo(curX + (-isNeg * recoilEffect), curY, 0.05f),
                            moveTo(curX + (isNeg * recoilEffect), curY, 0.05f))
            );*/
        }
        return false;
    }
}

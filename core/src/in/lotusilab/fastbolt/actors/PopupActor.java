package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import in.lotusilab.fastbolt.InitValues;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by rukmani on 12-07-2018.
 */
public class PopupActor extends Image {
    protected float halfWidth, halfHeight;
    protected float actualSize, smallest, shrinkTo, 
            entryTime, effectTime, exitTime, invisibleAlpha, visibleAlpha;
    protected Vector2 position;
    protected Stage stage;

    public PopupActor(TextureRegion region, Stage stage){
        super(region);

        this.actualSize = InitValues.SPRITE_SCALE;
        this.setScale(actualSize);
        
        smallest = 0.0f;
        shrinkTo = actualSize * 0.8f;
        
        entryTime = 0.1f;
        effectTime = 0.2f;
        exitTime = 0.03f;
        
        invisibleAlpha = 0.0f;
        visibleAlpha = 1.0f;

        halfHeight = (region.getRegionHeight() * InitValues.SPRITE_SCALE) * 0.2f;
        halfWidth = (region.getRegionWidth() * InitValues.SPRITE_SCALE) * 0.5f;

        position = new Vector2();

        this.stage = stage;

    }

    public void activate(float x, float y){
        System.out.println(x);
        position.set(x - halfWidth, y - halfHeight);
        Interpolation i = Interpolation.bounce;
        super.clearActions();
        super.addAction(
                sequence(
                        parallel(
                                moveTo(position.x, position.y),
                                alpha(invisibleAlpha),
                                scaleTo(0, 0)
                        ),
                        parallel(
                                alpha(visibleAlpha, entryTime),
                                scaleTo(actualSize, actualSize, entryTime, Interpolation.bounceIn)
                        ),
                        scaleTo(shrinkTo, shrinkTo, effectTime, i),
                        scaleTo(actualSize, actualSize, effectTime, i),
                        /*scaleTo(shrinkTo, shrinkTo, effectTime, i),
                        scaleTo(actualSize, actualSize, effectTime, i),
                        scaleTo(shrinkTo, shrinkTo, effectTime, i),
                        scaleTo(actualSize, actualSize, effectTime, i),
                        scaleTo(shrinkTo, shrinkTo, effectTime, i),
                        scaleTo(actualSize, actualSize, effectTime, i),*/
                        parallel(
                                scaleTo(smallest, smallest, 0.5f, Interpolation.circle),
                                moveTo(x, y, 0.5f, Interpolation.circle)
                                //rotateTo(360.0f, 0.5f, Interpolation.circle)
                                //moveTo(InitValues.ENEMY_SPAWN_POSITION.x - halfWidth, -10, 0.5f, Interpolation.circle)
                        )
                )

        );

        stage.addActor(this);
    }

    public boolean isFree(){
        return !hasActions();
    }
}

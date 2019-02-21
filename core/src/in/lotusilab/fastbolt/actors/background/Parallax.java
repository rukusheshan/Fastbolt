package in.lotusilab.fastbolt.actors.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import in.lotusilab.fastbolt.InitValues;

/**
 * Created by rukmani on 13-07-2018.
 */
public class Parallax extends Background {

    public Parallax(TextureRegion region, float positionX, float positionY,
                    float scaleX, float scaleY, float offset){
        super(region, positionX, positionY, scaleX, scaleY);
    }
}

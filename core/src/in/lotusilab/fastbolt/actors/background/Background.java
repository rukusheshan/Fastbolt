package in.lotusilab.fastbolt.actors.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by rukmani on 13-07-2018.
 */
public class Background extends Image {

    protected float scaleX, scaleY;
    public Background(TextureRegion region, float positionX, float positionY,
                      float scaleX, float scaleY){
        super(region);
        setScale(scaleX, scaleY);
        setPosition(positionX, positionY);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
}

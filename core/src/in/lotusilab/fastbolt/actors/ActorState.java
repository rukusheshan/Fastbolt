package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

/**
 * Created by rukmani on 29-04-2018.
 */
public class ActorState extends Animation<Image> {
    protected float elapsedTime;
    protected int numFrames;

    public ActorState(float frameDuration, Array<? extends Image> keyFrames,
                      PlayMode playMode, int numFrames) {
        super(frameDuration, keyFrames, playMode);
        this.numFrames = numFrames;
    }

    public void reset(){
        elapsedTime = 0.0f;
    }

    @Override
    public Image getKeyFrame(float delta) {
        elapsedTime += delta;
        return super.getKeyFrame(elapsedTime);
    }
}

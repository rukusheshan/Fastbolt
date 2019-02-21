package in.lotusilab.fastbolt.actors.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import in.lotusilab.fastbolt.InitValues;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by rukmani on 13-07-2018.
 */
public class Loiter extends Background {

    protected Vector2 position;
    protected float moveBy;
    protected float minRestTime, restTime, moveDuration;

    public Loiter(TextureRegion region, float positionX, float positionY,
                  float scaleX, float scaleY, float moveBy,
                  float moveDuration, float minRestTime){
        super(region, positionX, positionY, scaleX, scaleY);

        this.position = new Vector2(positionX, positionY);
        this.moveBy = moveBy;
        this.moveDuration = moveDuration;
        this.minRestTime = minRestTime;
        restTime = 0.0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!hasActions()) {
            restTime += delta;
            if (restTime > minRestTime) {
                loiter();
            }
        }
    }

    protected void loiter(){
        restTime = 0.0f;

        int isNeg = MathUtils.randomSign();
        addAction(moveTo(position.x + (isNeg * moveBy),
                position.y, moveDuration));

        if (isNeg < 0) setScale(-scaleX, scaleY);
        else setScale(scaleX, scaleY);
    }
}

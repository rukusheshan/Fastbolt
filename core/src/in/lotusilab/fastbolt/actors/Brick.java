package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import in.lotusilab.fastbolt.InitValues;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by rukmani on 4/26/17.
 */
public class Brick extends GameCharacter {


    public Brick(Image image, Body physicsBody) {
        super(image, physicsBody, InitValues.SPRITE_SCALE);
        setZIndex(InitValues.PLATFORM_Z_INDEX);
        physicsBody.setUserData(this);
        /*        this.image.setPosition(MathUtils.randomSign() * MathUtils.random(1000),
                MathUtils.randomSign() * MathUtils.random( 600));
        */

        this.actorType = InitValues.ActorType.BRICK;

        this.activeImage.setPosition(physicsBody.getPosition().x,
                physicsBody.getPosition().y - (MathUtils.randomSign() * 2 * InitValues.ASP_HEIGHT));

        float time = 0.001f;//MathUtils.random(2.f, 3.f);
        Interpolation interpolation = getRandomEffect();

        this.activeImage.addAction(
                sequence(
                        scaleTo(InitValues.SPRITE_SCALE / 10.0f, InitValues.SPRITE_SCALE / 10.0f),
                        alpha(0.0f),
                        parallel(
                                fadeIn(time),//, interpolation),
                                scaleTo(InitValues.SPRITE_SCALE, InitValues.SPRITE_SCALE, time),
                                moveTo(this.physicsBody.getPosition().x - halfWidth,
                                        this.physicsBody.getPosition().y - halfHeight,
                                        time))));//time, interpolation))));
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


    private Interpolation getRandomBounce(){
        Interpolation randomEffect;// = Interpolation.sine;
        int r = MathUtils.random(2);
        switch (r) {
            case 0:
                randomEffect = Interpolation.bounce;
                break;
            case 1:
                randomEffect = Interpolation.bounceIn;
                break;
            case 2:
                randomEffect = Interpolation.bounceOut;
                break;
            default:
                randomEffect = Interpolation.bounce;
                break;
        }
        return randomEffect;
    }

    private Interpolation getRandomCircle(){
        Interpolation randomEffect;// = Interpolation.sine;
        int r = MathUtils.random(2);
        switch (r) {
            case 0:
                randomEffect = Interpolation.circle;
                break;
            case 1:
                randomEffect = Interpolation.circleIn;
                break;
            case 2:
                randomEffect = Interpolation.circleOut;
                break;
            default:
                randomEffect = Interpolation.circle;
                break;
        }
        return randomEffect;
    }

    private Interpolation getRandomElastic(){
        Interpolation randomEffect;// = Interpolation.sine;
        int r = MathUtils.random(2);
        switch (r) {
            case 0:
                randomEffect = Interpolation.elastic;
                break;
            case 1:
                randomEffect = Interpolation.elasticIn;
                break;
            case 2:
                randomEffect = Interpolation.elasticOut;
                break;
            default:
                randomEffect = Interpolation.elastic;
                break;
        }
        return randomEffect;
    }

    private Interpolation getRandomExp5(){
        Interpolation randomEffect;// = Interpolation.sine;
        int r = MathUtils.random(2);
        switch (r) {
            case 0:
                randomEffect = Interpolation.exp5;
                break;
            case 1:
                randomEffect = Interpolation.exp5In;
                break;
            case 2:
                randomEffect = Interpolation.exp5Out;
                break;
            default:
                randomEffect = Interpolation.exp5;
                break;
        }
        return randomEffect;
    }

    private Interpolation getRandomExp10(){
        Interpolation randomEffect;// = Interpolation.sine;
        int r = MathUtils.random(2);
        switch (r) {
            case 0:
                randomEffect = Interpolation.exp10;
                break;
            case 1:
                randomEffect = Interpolation.exp10In;
                break;
            case 2:
                randomEffect = Interpolation.exp10Out;
                break;
            default:
                randomEffect = Interpolation.exp10;
                break;
        }
        return randomEffect;
    }

    private Interpolation getRandomPow2(){
        Interpolation randomEffect;// = Interpolation.sine;
        int r = MathUtils.random(4);
        switch (r) {
            case 0:
                randomEffect = Interpolation.pow2;
                break;
            case 1:
                randomEffect = Interpolation.pow2In;
                break;
            case 2:
                randomEffect = Interpolation.pow2Out;
                break;
            case 3:
                randomEffect = Interpolation.pow2InInverse;
                break;
            case 4:
                randomEffect = Interpolation.pow2OutInverse;
                break;
            default:
                randomEffect = Interpolation.pow2;
                break;
        }
        return randomEffect;
    }

    private Interpolation getRandomPow4(){
        Interpolation randomEffect;// = Interpolation.sine;
        int r = MathUtils.random(4);
        switch (r) {
            case 0:
                randomEffect = Interpolation.pow3;
                break;
            case 1:
                randomEffect = Interpolation.pow3In;
                break;
            case 2:
                randomEffect = Interpolation.pow3Out;
                break;
            case 3:
                randomEffect = Interpolation.pow3InInverse;
                break;
            case 4:
                randomEffect = Interpolation.pow3OutInverse;
                break;
            default:
                randomEffect = Interpolation.pow2;
                break;
        }
        return randomEffect;
    }

    private Interpolation getRandomEffect() {
        Interpolation randomEffect;// = Interpolation.sine;
        int r = 0;//MathUtils.random(3);
        switch (r){
            case 0:
                randomEffect = getRandomBounce();
                break;
            case 1:
                randomEffect = getRandomCircle();
                break;
            case 2:
                randomEffect = getRandomElastic();
                break;
            case 3:
                randomEffect = getRandomExp5();
                break;
            /*
            case 26:
                randomEffect = Interpolation.pow4;
                break;
            case 27:
                randomEffect = Interpolation.pow4In;
                break;
            case 28:
                randomEffect = Interpolation.pow4Out;
                break;
            case 29:
                randomEffect = Interpolation.pow5;
                break;
            case 30:
                randomEffect = Interpolation.pow5In;
                break;
            case 31:
                randomEffect = Interpolation.pow5Out;
                break;
            case 32:
                randomEffect = Interpolation.sine;
                break;
            case 33:
                randomEffect = Interpolation.sineIn;
                break;
            case 34:
                randomEffect = Interpolation.sineOut;
                break;
            case 35:
                randomEffect = Interpolation.swing;
                break;
            case 36:
                randomEffect = Interpolation.swingIn;
                break;
            case 37:
                randomEffect = Interpolation.swingOut;
                break;*/
            default:
                randomEffect = Interpolation.bounce;
                break;
        }
        return randomEffect;
    }
}

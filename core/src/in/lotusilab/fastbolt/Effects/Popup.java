package in.lotusilab.fastbolt.Effects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.pool.PopupPool;

/**
 * Created by rukmani on 11-07-2018.
 */
public class Popup {
    private TextureAtlas atlas;
    private Stage stage;
    private ArrayList<PopupPool> kill, doubleKill, death, zeroDeath, upgrade;

    public Popup(TextureAtlas atlas, Stage stage) {
        this.atlas = atlas;
        this.stage = stage;
        init();
    }

    private void init(){
        kill = createPools(InitValues.KILLBALLOON);
        doubleKill = createPools(InitValues.DOUBLEKILLBALLOON);
        death = createPools(InitValues.DEATHBALLOON);
        zeroDeath = createPools(InitValues.ZERODEATHBALLOON);
        upgrade = createPools(InitValues.UPGRADEBALLOON);
    }

    private ArrayList<PopupPool> createPools(String[] strings){
        ArrayList<PopupPool> retVal = new ArrayList<PopupPool>();
        for (String s : strings){
            retVal.add(new PopupPool(atlas, s, stage));
        }
        return retVal;
    }

    public void show(Vector2 position, InitValues.BalloonType type){
        switch (type){
            case KILL:
                kill.get(MathUtils.random(kill.size()-1)).getPopup().activate(position.x, position.y);
                break;
            case DOUBLEKILL:
                doubleKill.get(MathUtils.random(doubleKill.size()-1)).getPopup().activate(position.x, position.y);
                break;
            case DEATH:
                death.get(MathUtils.random(death.size()-1)).getPopup().activate(position.x, position.y);
                break;
            case ZERODEATH:
                zeroDeath.get(MathUtils.random(zeroDeath.size()-1)).getPopup().activate(position.x, position.y);
                break;
            case UPGRADE:
                upgrade.get(MathUtils.random(upgrade.size()-1)).getPopup().activate(position.x, position.y);
                break;
        }
    }
}

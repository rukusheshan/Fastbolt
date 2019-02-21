package in.lotusilab.fastbolt.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import in.lotusilab.fastbolt.actors.PopupActor;

/**
 * Created by rukmani on 12-07-2018.
 */
public class PopupPool {

    private TextureAtlas atlas;
    private String name;
    private Stage stage;

    private ArrayList<PopupActor> popups;

    public PopupPool(TextureAtlas atlas, String name, Stage stage){
        this.atlas = atlas;
        this.name = name;
        this.stage = stage;

        popups = new ArrayList<PopupActor>();
    }

    public PopupActor getPopup(){
        for (PopupActor p : popups){
            if (p.isFree())
                return p;
        }
        return createNew();
    }

    private PopupActor createNew(){
        PopupActor p = new PopupActor(atlas.findRegion(name), stage);
        popups.add(p);
        return p;
    }
}

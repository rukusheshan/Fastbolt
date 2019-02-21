package in.lotusilab.fastbolt.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import in.lotusilab.fastbolt.InitValues;
import in.lotusilab.fastbolt.pool.EnemyPool;

/**
 * Created by rukmani on 20-06-2018.
 */

public class EntryPortal extends GameCharacter {

    private class SpawnTimerLocal {
        float spawnedBefore, minTime, maxTime, offset, currOffset;
        int multi, range, prob;

        public SpawnTimerLocal(float spawnedBefore, float minTime, float maxTime, float offset,
                               int multi, int mod, int prob) {
            this.spawnedBefore = spawnedBefore;
            this.minTime = minTime;
            this.maxTime = maxTime;
            this.offset = offset;
            this.multi = multi;
            this.range = mod;
            this.prob = prob;
            currOffset = 0.0f;
        }
    }

    protected ArrayList<EnemyPool> enemyPools;

    protected ArrayList<InitValues.SpawnTimeData> timerData;
    protected ArrayList<SpawnTimerLocal> localTimer;

    protected int unlockedEnemies;

    public EntryPortal(ActorState state, Body physicsBody,
                       ArrayList<InitValues.SpawnTimeData> timerData,
                       ArrayList<EnemyPool> enemyPools, int unlockedEnemies){

        super(state, physicsBody, InitValues.SPRITE_SCALE);

        physicsBody.setUserData(this);

        setActorType(InitValues.ActorType.ENTRY_PORTAL);

        this.timerData = timerData;

        localTimer = new ArrayList<SpawnTimerLocal>(timerData.size());
        for (InitValues.SpawnTimeData data : timerData)
            localTimer.add(createTimer(data));

        this.enemyPools = enemyPools;
        this.unlockedEnemies = unlockedEnemies;
    }

    protected SpawnTimerLocal createTimer(InitValues.SpawnTimeData data){
        return new SpawnTimerLocal(
                data.minTime,
                data.minTime,
                data.maxTime,
                data.offset,
                1,
                data.range,
                data.prob);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {

        updateTimer(delta);

        for (int i = 0; i < unlockedEnemies; ++i)
            spawnBots(enemyPools.get(i), localTimer.get(i));

        super.act(delta);
    }

    private void updateTimer(float delta) {
        for (SpawnTimerLocal timer : localTimer){
            timer.spawnedBefore += delta;
            timer.currOffset += delta;
        }
    }

    protected void spawnBots(EnemyPool pool, SpawnTimerLocal timer){

        if (timer.multi > 0){
            if (timer.currOffset > timer.offset) {
                --timer.multi;
                pool.send();

                timer.currOffset = 0.0f;
                timer.spawnedBefore = 0.0f;
            }
        }

        else if (timer.spawnedBefore > timer.minTime) {
            int r = MathUtils.random(100);

            if (r > timer.prob) {
                timer.multi = MathUtils.random(timer.range);
                timer.currOffset = timer.offset;
            }

            else if (timer.spawnedBefore > timer.maxTime) {
                timer.multi = 1;
                timer.currOffset = timer.offset;
            }
        }
    }
}

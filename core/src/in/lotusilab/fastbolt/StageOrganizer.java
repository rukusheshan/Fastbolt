package in.lotusilab.fastbolt;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import in.lotusilab.fastbolt.actors.ActorState;
import in.lotusilab.fastbolt.actors.Brick;
import in.lotusilab.fastbolt.actors.Chitti;
import in.lotusilab.fastbolt.actors.Crate;
import in.lotusilab.fastbolt.actors.EntryPortal;
import in.lotusilab.fastbolt.actors.ExitPortal;
import in.lotusilab.fastbolt.actors.Trigger;
import in.lotusilab.fastbolt.actors.background.Loiter;
import in.lotusilab.fastbolt.actors.background.Parallax;
import in.lotusilab.fastbolt.actors.weapons.SingleFire;
import in.lotusilab.fastbolt.actors.weapons.Weapon;
import in.lotusilab.fastbolt.pool.EnemyPool;
import in.lotusilab.fastbolt.screens.GameScreen;

/**
 * Created by rukmani on 29-04-2018.
 */
public class StageOrganizer {

    public void organizeStage(Group background, Group levelGroup, Group enemyGroup,
                              Group playerGroup, Group weaponGroup, World physicsWorld,
                              GameScreen screen, InitValues.LevelDetails levelDetails,
                              TextureAtlas atlas){

        String stageLayout = levelDetails.levelLayout;

        createBackgroundElements(levelDetails.elementsByOrderFromBehind, atlas, background);

        Vector3 position = new Vector3(InitValues.LEFT_TOP);
        position.x += InitValues.TILE_OFFSET;

        float offset = InitValues.TILE_OFFSET;

        Crate crate = createWeaponSystem(weaponGroup, physicsWorld, atlas, position);
        Chitti chitti = createChitti(physicsWorld, screen, atlas, position, crate);

        for(int i = 0; i < stageLayout.length(); ++i, position.x += offset){

            char c = stageLayout.charAt(i);

            switch (c){
                case '1':
                    createBrick(levelGroup, physicsWorld, atlas, position);
                    break;

                case '2':
                    levelGroup.addActor(new Brick(
                            createRenderComponent(atlas, InitValues.BRICK_REG),
                            createPhysicsBody(physicsWorld, position, InitValues.BRICK_PHYSICS_DATA)));
                    break;

                case '3':
                    chitti.setPosition(position.x, position.y);
                    break;

                case '4':
                    position.x += offset * 0.5f;
                    levelGroup.addActor(createEntryPortal(screen, atlas, physicsWorld, enemyGroup, position));
                    position.x -= offset * 0.5f;
                    //entities.add(loadEntryPortal(position));
                    break;

                case '5':
                    position.x += offset * 0.5f;
                    levelGroup.addActor(createExitPortal(atlas, physicsWorld, position));
                    position.x -= offset * 0.5f;
                    //entities.add(loadExitPortal(position));
                    break;

                case '6':
                    crate.addSpawnPosition(new Vector2(position.x, position.y));
                    break;

                case '7':
                case '8':
                    createBrick(levelGroup, physicsWorld, atlas, position);
                    position.y += offset;
                    new Trigger(createPhysicsBody(physicsWorld, position,
                            InitValues.JUMP_TRIGGER_PHYSICS_DATA));
                    position.y -= offset;
                    break;
                case '\n':
                    position.x = InitValues.LEFT_TOP.x;
                    position.y -= offset;
                    break;

                default:
                    break;

            }
        }
        weaponGroup.addActor(crate);
        playerGroup.addActor(chitti);
    }

    private void createBrick(Group group, World physicsWorld, TextureAtlas atlas, Vector3 position) {
        Brick platform = new Brick(
                createRenderComponent(atlas, InitValues.PLATFORM_REG),
                createPhysicsBody(physicsWorld, position, InitValues.BRICK_PHYSICS_DATA));
        platform.setActorType(InitValues.ActorType.PLATFORM);
        group.addActor(platform);
    }

    private void createBackgroundElements(InitValues.BGElement[] backgroundElements,
                                          TextureAtlas atlas, Group group) {
        for (InitValues.BGElement bg : backgroundElements)
            switch (bg.type){
                case Parallax:
                    InitValues.ParallaxElement pe = (InitValues.ParallaxElement)bg;
                    group.addActor(new Parallax(atlas.findRegion(pe.name),
                            pe.positionX, pe.positionY,
                            pe.scaleX, pe.scaleY,
                            pe.offset));
                    break;
                case Loiter:
                    InitValues.LoiteringElement le = (InitValues.LoiteringElement)bg;
                    group.addActor(new Loiter(atlas.findRegion(le.name),
                            le.positionX, le.positionY,
                            le.scaleX, le.scaleY,
                            le.moveBy, le.moveDuration, le.minRestTime));
                    break;
            }
    }

    private Chitti createChitti(World physicsWorld, GameScreen screen,
                                TextureAtlas atlas, Vector3 position, Crate crate){

        ArrayList<InitValues.TextureSequenceData> playerRenderData =
                new ArrayList<InitValues.TextureSequenceData>();

        playerRenderData.add(InitValues.PLAYER_IDLE_SEQ_RENDER_DATA);
        playerRenderData.add(InitValues.PLAYER_WALK_SEQ_RENDER_DATA);
        playerRenderData.add(InitValues.PLAYER_JUMP_SEQ_RENDER_DATA);

        Chitti chitti = new Chitti(
                createRenderComponent(atlas, playerRenderData),
                createPhysicsBody(physicsWorld, position, InitValues.PLAYER_PHYSICS_DATA),
                physicsWorld,
                crate.getDefaultWeapon());
        screen.setChitti(chitti);
        return chitti;
    }

    private Crate createWeaponSystem(Group group, World physicsWorld,
                                     TextureAtlas atlas, Vector3 position) {

        Crate crate = new Crate(
                createActorState(atlas, InitValues.CRATE_SEQ),
                createPhysicsBody(physicsWorld, position, InitValues.CRATE_PHYSICS_DATA),
                createWeapons(physicsWorld, group, atlas));

        return crate;
    }

    private ArrayList<Weapon> createWeapons(World physicsWorld, Group group, TextureAtlas atlas){
        ArrayList<Weapon> weapons = new ArrayList<Weapon>();


        //Creating Pistol
        weapons.add(createWeapon(physicsWorld, group, atlas, InitValues.PISTOL_DATA, true));

        //Creating MachineGun
        weapons.add(createWeapon(physicsWorld, group, atlas, InitValues.MACHINE_GUN_DATA, false));

        //Creating Ingram
        weapons.add(createWeapon(physicsWorld, group, atlas, InitValues.INGRAM_DATA, false));

        return  weapons;
    }

    private Weapon createWeapon(World physicsWorld, Group group, TextureAtlas atlas,
                                InitValues.WeaponData weaponData, boolean singleFire){

        Image weaponImage = createRenderComponent(atlas, weaponData.textureRegionData);
        Body body = createPhysicsBody(physicsWorld, weaponData.offsetPosition, weaponData.physicsBodyData);

        if (singleFire)
            return new SingleFire(weaponImage, body, physicsWorld, group, atlas,
                    new Vector2(weaponData.offsetPosition.x, weaponData.offsetPosition.y),
                    weaponData.recoilEffect, weaponData.recoilForce, weaponData.ammoData);
        else
            return new Weapon(weaponImage, body, physicsWorld, group, atlas,
                    new Vector2(weaponData.offsetPosition.x, weaponData.offsetPosition.y),
                    weaponData.recoilEffect, weaponData.recoilForce, weaponData.ammoData);
    }

    private Image createRenderComponent(TextureAtlas atlas, String textureRegion){
        return new Image(atlas.findRegion(textureRegion));
    }

    private ArrayList<ActorState> createRenderComponent(TextureAtlas atlas,
                                                        ArrayList<InitValues.TextureSequenceData> sequenceData){
        ArrayList<ActorState> states = new ArrayList<ActorState>(sequenceData.size());
        for (InitValues.TextureSequenceData data : sequenceData) {
            states.add(createActorState(atlas, data));
        }
        return states;
    }

    private ActorState createActorState(TextureAtlas atlas,
                                             InitValues.TextureSequenceData data){
        Array<Image> images = new Array<Image>(data.seqNum);
        for (int i = 1; i < data.seqNum + 1; ++i) {
            Image image = new Image(atlas.findRegion(data.seqName + i));
            image.setScale(InitValues.SPRITE_SCALE);
            images.add(image);
        }
        return new ActorState(data.duration, images, Animation.PlayMode.LOOP, data.seqNum);
    }

    private ExitPortal createExitPortal(TextureAtlas atlas, World physicsWorld, Vector3 position){

        return new ExitPortal(
                createActorState(atlas, InitValues.EXIT_REG_SEQ),
                createPhysicsBody(physicsWorld, position, InitValues.EXIT_PORTAL_PHYSICS_DATA),
                createPhysicsBody(physicsWorld, position, InitValues.EXIT_GATE_PHYSICS_DATA)
        );
    }

    private EntryPortal createEntryPortal(GameScreen screen, TextureAtlas atlas, World physicsWorld,
                                          Group group, Vector3 position){

        ArrayList<EnemyPool> enemyPools = new ArrayList<EnemyPool>();
        enemyPools.add(new EnemyPool(screen, physicsWorld, group, atlas, InitValues.RUST_DATA));
        enemyPools.add(new EnemyPool(screen, physicsWorld, group, atlas, InitValues.BANGBANG_DATA));
        enemyPools.add(new EnemyPool(screen, physicsWorld, group, atlas, InitValues.SPIKE_DATA));

        ArrayList<InitValues.SpawnTimeData> times = new ArrayList<InitValues.SpawnTimeData>();
        times.add(InitValues.RUST_TIME);
        times.add(InitValues.BANGBANG_TIME);
        times.add(InitValues.SPIKE_TIME);

        return new EntryPortal(
                createActorState(atlas, InitValues.ENTRY_REG_SEQ),
                createPhysicsBody(physicsWorld, position, InitValues.ENTRY_PORTAL_PHYSICS_DATA),
                times, enemyPools, 2);
    }

    private Body createPhysicsBody(World physicsWorld, Vector3 position,
                                   InitValues.PhysicsBodyData physicsBodyData){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = physicsBodyData.getType();
        bodyDef.position.set(position.x, position.y);
        bodyDef.fixedRotation = true;

        Body physicsBody = physicsWorld.createBody(bodyDef);

        Shape shape;

        if (physicsBodyData.isCircle()) {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(physicsBodyData.getHalfWidth());
            shape = circleShape;
        }
        else {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(physicsBodyData.getHalfWidth(), physicsBodyData.getHalfHeight());
            shape = polygonShape;
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = physicsBodyData.getRestitution();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;

        if (physicsBodyData.isSensor())
            fixtureDef.isSensor = true;

        fixtureDef.filter.maskBits = physicsBodyData.getMask();
        fixtureDef.filter.categoryBits = physicsBodyData.getCategory();

        physicsBody.createFixture(fixtureDef);

        return physicsBody;
    }
}

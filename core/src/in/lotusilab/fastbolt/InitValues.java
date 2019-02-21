package in.lotusilab.fastbolt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

import in.lotusilab.fastbolt.actors.background.Background;

/**
 * Created by rukmani on 3/13/17.
 */
public class InitValues {

    public static final String SCREEN_TITLE = "Doodle Wars";

    public static final int SCREEN_WIDTH        = 1280;//1600
    public static final int SCREEN_HEIGHT       = 720;//900

    public static final float ASP_WIDTH         = 89.6f;
    public static final float ASP_HEIGHT        = 51.2f;

    public static final float SPRITE_SCALE      = 0.1f;

    public static final int PLATFORM_Z_INDEX = 1;
    public static final int GAMEELEMENTS_Z_INDEX = 2;
    public static final int UI_Z_INDEX = Integer.MAX_VALUE;
    /*public static final int PLATFORM_Z_INDEX = 1;
    public static final int PLATFORM_Z_INDEX = 1;*/

    public static final short BRICK_CATEGORY    = 1;
    public static final short ENEMY_CATEGORY    = 2;
    public static final short AMMO_CATEGORY     = 4;
    public static final short PLAYER_CATEGORY   = 8;
    public static final short CRATE_CATEGORY    = 16;
    public static final short WEAPON_CATEGORY   = 32;
    public static final short TRIGGER_CATEGORY  = 64;

    //file locations
    public static final String LOGO_FILE        = "badlogic.jpg";
    public static final String TEXTURE_ATLAS    = "atlas/fastbolt.pack";
    public static final String COMING_SOON_FONT = "font/ComingSoon.ttf";
    public static final String BASIC_SKIN_ATLAS = "skin/uiskin.atlas";
    public static final String BASIC_SKIN_JSON  = "skin/uiskin.json";


    public static final String PLATFORM_REG  = "platform";
    public static final String BRICK_REG     = "brick";

    public static class ButtonData{
        public String name;
        public float posX, posY, scaleX, scaleY;

        public ButtonData(String name, float posX, float posY, float scaleX, float scaleY) {
            this.name = name;
            this.posX = posX;
            this.posY = posY;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
    }

    public static final float BUTTON_ALPHA  = 0.7F;

    public static final ButtonData LEFT_BUTTON_DATA = new ButtonData("left", 4, 1, 0.17f, 0.17f);
    public static final ButtonData RIGHT_BUTTON_DATA = new ButtonData("right", 15, 1, 0.17f, 0.17f);
    public static final ButtonData JUMP_BUTTON_DATA = new ButtonData("jump", ASP_WIDTH - 19, 10, 0.17f, 0.17f);
    public static final ButtonData SHOOT_BUTTON_DATA = new ButtonData("weaponattack", ASP_WIDTH - 25, 1, 0.17f, 0.17f);
    public static final ButtonData ATTACK_BUTTON_DATA = new ButtonData("armattack", ASP_WIDTH - 13, 1, 0.17f, 0.17f);


    public enum BalloonType {
        KILL,
        DOUBLEKILL,
        DEATH,
        ZERODEATH,
        UPGRADE
    }

    //kill
    public static final String WOW          = "wow";
    public static final String POOW         = "poow";
    public static final String BOOM         = "boom";
    public static final String BANG         = "bang";
    public static final String POW          = "pow";
    public static final String POP          = "pop";

    public static final String[] KILLBALLOON = new String[]{WOW, POOW, BOOM, BANG, POW, POP};

    //doubleKill
    public static final String BOOOM        = "booom";
    public static final String POWPOW       = "powpow";
    public static final String KISSME       = "kissme";
    public static final String OHYEAH       = "ohyeah";
    public static final String CRAAASH      = "craaash";

    public static final String[] DOUBLEKILLBALLOON = new String[]{BOOM, POWPOW, KISSME, OHYEAH, CRAAASH};

    //death
    public static final String SHIT         = "shit";
    public static final String DAMN         = "damn";
    public static final String OMG          = "omg";

    public static final String[] DEATHBALLOON = new String[]{SHIT, DAMN, OMG};

    //zeroDeath
    public static final String EPICSHIT     = "epicshit";
    public static final String WTF          = "wtf";

    public static final String[] ZERODEATHBALLOON = new String[]{EPICSHIT, WTF};

    //upgrade
    public static final String HELLYEAH     = "hellyeah";
    public static final String THANKYOU     = "thankyou";

    public static final String[] UPGRADEBALLOON = new String[]{HELLYEAH, THANKYOU};

    public static class TextureSequenceData{

        public String seqName;
        public int seqNum;
        public float duration;

        public TextureSequenceData(String regionSeqName, int regionSeqNum, float duration){
            this.seqName = regionSeqName;
            this.seqNum = regionSeqNum;
            this.duration = duration;
        }

    }

    public static class PhysicsBodyData {

        private boolean circle;
        private float halfWidth, halfHeight, restitution;
        private BodyDef.BodyType type;
        private boolean sensor;
        short mask, category;

        public PhysicsBodyData(float halfWidth, float halfHeight, boolean circle, float restitution,
                               BodyDef.BodyType type, boolean sensor, short category, short mask) {
            this.halfWidth = halfWidth;
            this.halfHeight = halfHeight;
            this.circle = circle;
            if (circle && halfHeight != halfWidth)
                System.out.println("size data ambiguous as this will be used as radius !!!");

            this.sensor = sensor;
            this.restitution = restitution;
            this.type = type;
            this.category = category;
            this.mask = mask;
        }

        public boolean isCircle() {
            return circle;
        }

        public boolean isSensor() {
            return sensor;
        }

        public float getHalfWidth() {
            return halfWidth;
        }

        public float getHalfHeight() {
            return halfHeight;
        }

        public float getRestitution() {
            return restitution;
        }

        public BodyDef.BodyType getType() {
            return type;
        }

        public short getMask() {
            return mask;
        }

        public short getCategory() {
            return category;
        }
    }

    public static class EnemyData{
        public TextureSequenceData walkSeq, jumpSeq;
        public PhysicsBodyData physicsBodyData;
        public float velocity, health;

        public EnemyData(TextureSequenceData walkSeq, TextureSequenceData jumpSeq,
                         PhysicsBodyData physicsBodyData, float velocity, float health){
            this.walkSeq = walkSeq;
            this.jumpSeq = jumpSeq;
            this.physicsBodyData = physicsBodyData;
            this.velocity = velocity;
            this.health = health;
        }
    }

    public static class AmmoData {

        public String textureRegionData;
        public PhysicsBodyData physicsBodyData;
        public Vector2 force, offsetPosition;
        public float damage;

        public AmmoData(String textureRegionData, PhysicsBodyData physicsBodyData,
                        Vector2 offsetPosition, Vector2 force, float damage){
            this.textureRegionData = textureRegionData;
            this.physicsBodyData = physicsBodyData;
            this.offsetPosition = offsetPosition;
            this.force = force;
            this.damage = damage;
        }

    }

    public static class WeaponData {
        public String textureRegionData;
        public PhysicsBodyData physicsBodyData;
        public Vector3 offsetPosition;
        public float recoilEffect, recoilForce;
        public AmmoData ammoData;

        public WeaponData(String textureRegionData, PhysicsBodyData physicsBodyData,
                          Vector3 offsetPosition, float recoilEffect, float recoilForce,
                          AmmoData ammoData) {
            this.textureRegionData = textureRegionData;
            this.physicsBodyData = physicsBodyData;
            this.offsetPosition = offsetPosition;
            this.recoilEffect = recoilEffect;
            this.recoilForce = recoilForce;
            this.ammoData = ammoData;
        }
    }

    public static final TextureSequenceData CRATE_SEQ     = new TextureSequenceData("crate",
            23,
            0.06f);

    public static final TextureSequenceData ENTRY_REG_SEQ = new TextureSequenceData("entryportal",
            23,
            0.09f);

    public static final TextureSequenceData EXIT_REG_SEQ = new TextureSequenceData("exitportal",
            15,
            0.09f);

    public static final TextureSequenceData RUSTY_WALK_SEQ = new TextureSequenceData("rustywalk",
            8,
            0.16f);

    public static final TextureSequenceData RUSTY_JUMP_SEQ = new TextureSequenceData("rustyjump",
            5,
            0.16f);

    public static final TextureSequenceData BANGBANG_WALK_SEQ = new TextureSequenceData("bangbangwalk",
            3,
            0.16f);

    public static final TextureSequenceData BANGBANG_JUMP_SEQ = new TextureSequenceData("bangbangjump",
            1,
            0.16f);

    public static final TextureSequenceData SPIKE_WALK_SEQ = new TextureSequenceData("spike",
            4,
            0.05f);

    public static final TextureSequenceData SPIKE_JUMP_SEQ = new TextureSequenceData("spike",
            1,
            0.05f);


    public enum ActorType {
        PLAYER,
        ENEMY,
        BRICK,
        PLATFORM,
        CRATE,
        AMMO,
        WEAPON,
        TRIGGER,
        ENTRY_PORTAL,
        EXIT_PORTAL
    }

    public static final float PLAYER_IDLE_ANIMATION_TIME = 0.08f;
    public static final float PLAYER_WALK_ANIMATION_TIME = 0.08f;
    public static final float PLAYER_JUMP_ANIMATION_TIME = 0.08f;

    public static final float PLAYER_TEXTURE_SIZE_X = 2f;
    public static final float PLAYER_TEXTURE_SIZE_Y = 2f;

    public static final int PLAYER_IDLE_STATE_INDEX = 0;
    public static final int PLAYER_WALK_STATE_INDEX = 1;
    public static final int PLAYER_JUMP_STATE_INDEX = 2;


    public static final float PLAYER_LEFT_SPEED     = -65.0f;
    public static final float PLAYER_RIGHT_SPEED    = 65.0f;
    public static final float PLAYER_LEFT_ON_AIR_SPEED     = -100.0f;
    public static final float PLAYER_RIGHT_ON_AIR_SPEED    = 100.0f;


    public static final int PLAYER_SPRITE_SET   = 3;

    /*public static final float PLAYER_MOVEMENT_MAX_SPEED         = 100;
    public static final float PLAYER_MOVEMENT_ACCELERATION      = 200;
    public static final float PLAYER_MOVEMENT_DRAG              = 600;
    public static final float PLAYER_MOVEMENT_GRAVITY           = -30;
    public static final float PLAYER_MOVEMENT_MAX_DOWN_SPEED    = -20;*/
    public static final float PLAYER_MOVEMENT_JUMP_SPEED        = 500;
    public static final int   PLAYER_MAX_JUMP_COUNT             = 8;

    public static final float RAY_PROJECTION                    = 0.001f;

    public static final TextureSequenceData PLAYER_IDLE_SEQ_RENDER_DATA = new TextureSequenceData("playeridle",
            5,
            PLAYER_IDLE_ANIMATION_TIME);

    public static final TextureSequenceData PLAYER_WALK_SEQ_RENDER_DATA = new TextureSequenceData("playerwalk",
            5,
            PLAYER_WALK_ANIMATION_TIME);

    public static final TextureSequenceData PLAYER_JUMP_SEQ_RENDER_DATA = new TextureSequenceData("playerjump",
            1,
            PLAYER_JUMP_ANIMATION_TIME);

    public static final String PISTOL_REG            = "pistol";
    public static final String MACHINE_GUN_REG       = "machinegun";
    //public static final String INGRAM_REG            = "ingram";
    public static final String ROCKETLAUNCHER_REG    = "rocketlauncher";
    public static final String GRENADE_LAUNCHER_REG  = "grenadelauncher";
    //public static final String FLAME_THROWER_REG     = "flameThrower";
    public static final String SHOTGUN_REG           = "shotgun";

    public static final String PELLET_REG           = "pellet";
    public static final String BULLET_REG           = "bullet";
    public static final String GRENADE_REG          = "grenade";
    public static final String ROCKET_REG          = "rocket";

    /// physics

    public static final Vector2 GRAVITY     = new Vector2(0.0f, -20.f);

    public static final PhysicsBodyData BRICK_PHYSICS_DATA = new PhysicsBodyData(
            1.6f,
            1.6f,
            false,
            0.0f,
            BodyDef.BodyType.StaticBody,
            false, //not a sensor
            BRICK_CATEGORY,
            (short) (ENEMY_CATEGORY | PLAYER_CATEGORY | AMMO_CATEGORY | CRATE_CATEGORY));

    public static final PhysicsBodyData JUMP_TRIGGER_PHYSICS_DATA = new PhysicsBodyData(
            0.8f,
            0.8f,
            false,
            0.0f,
            BodyDef.BodyType.StaticBody,
            true, // a sensor
            TRIGGER_CATEGORY,
            (short) (ENEMY_CATEGORY));

    public static final PhysicsBodyData EXIT_GATE_PHYSICS_DATA = new PhysicsBodyData(
            3.2f,
            1.6f,
            false,
            0.0f,
            BodyDef.BodyType.StaticBody,
            false, //not a sensor
            BRICK_CATEGORY,
            (short) (PLAYER_CATEGORY | CRATE_CATEGORY));

    public static final PhysicsBodyData EXIT_PORTAL_PHYSICS_DATA = new PhysicsBodyData(
            3.2f,
            0.2f,
            false,
            0.0f,
            BodyDef.BodyType.StaticBody,
            true, // sensor
            BRICK_CATEGORY,
            (short) (ENEMY_CATEGORY));

    public static final PhysicsBodyData ENTRY_PORTAL_PHYSICS_DATA = new PhysicsBodyData(
            3.2f,
            0.2f,
            false,
            0.0f,
            BodyDef.BodyType.StaticBody,
            true, //not a sensor
            BRICK_CATEGORY,
            (short) (ENEMY_CATEGORY));

    public static final PhysicsBodyData CRATE_PHYSICS_DATA = new PhysicsBodyData(
            0.4f,
            0.4f,
            false,
            0.0f,
            BodyDef.BodyType.DynamicBody,
            false, //can be a sensor
            CRATE_CATEGORY,
            (short) (PLAYER_CATEGORY | BRICK_CATEGORY));

    public static final PhysicsBodyData PLAYER_PHYSICS_DATA = new PhysicsBodyData(
            0.8f,
            0.8f,
            true, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.DynamicBody,
            false, //not a sensor
            PLAYER_CATEGORY,
            (short) (/*ENEMY_CATEGORY |*/ BRICK_CATEGORY | CRATE_CATEGORY));

    public static final PhysicsBodyData PISTOL_PHYSICS_DATA = new PhysicsBodyData(
            0.4f,
            0.3f,
            false, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.KinematicBody,
            true, // a sensor - simply
            WEAPON_CATEGORY,
            (short) (0));

    public static final PhysicsBodyData INGRAM_PHYSICS_DATA = new PhysicsBodyData(
            0.8f,
            0.3f,
            false, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.KinematicBody,
            true, // a sensor - simply
            WEAPON_CATEGORY,
            (short) (0));

    public static final PhysicsBodyData MACHINE_GUN_PHYSICS_DATA = new PhysicsBodyData(
            0.8f,
            0.3f,
            false, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.KinematicBody,
            true, // a sensor - simply
            WEAPON_CATEGORY,
            (short) (0));

    public static final PhysicsBodyData BULLET_PHYSICS_DATA = new PhysicsBodyData(
            0.3f,
            0.2f,
            false, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.DynamicBody,
            false, //not a sensor
            AMMO_CATEGORY,
            (short) (BRICK_CATEGORY | ENEMY_CATEGORY));

    public static final AmmoData BULLET_DATA        = new AmmoData(BULLET_REG, BULLET_PHYSICS_DATA,
            new Vector2(2.0f, 0.2f),
            new Vector2(20.0f, 0.0f),
            50.0f);

    public static final AmmoData AUTO_BULLET_DATA   = new AmmoData(BULLET_REG, BULLET_PHYSICS_DATA,
            new Vector2(5.0f, 0.2f),
            new Vector2(20.0f, 0.0f),
            20.0f);

    public static final AmmoData INGRAM_BULLET_DATA   = new AmmoData(BULLET_REG, BULLET_PHYSICS_DATA,
            new Vector2(5.0f, 0.2f),
            new Vector2(20.0f, 0.0f),
            15.0f);

    public static final WeaponData PISTOL_DATA      = new WeaponData(PISTOL_REG, PISTOL_PHYSICS_DATA,
            new Vector3(0.5f, 0.5f, 0.0f),
            0.5f,
            2.5f,
            BULLET_DATA);

    public static final WeaponData INGRAM_DATA      = new WeaponData(GRENADE_LAUNCHER_REG, INGRAM_PHYSICS_DATA,
            new Vector3(-2.5f, 0.5f, 0.0f),
            0.5f,
            2.5f,
            INGRAM_BULLET_DATA);

    public static final WeaponData MACHINE_GUN_DATA  = new WeaponData(MACHINE_GUN_REG, MACHINE_GUN_PHYSICS_DATA,
            new Vector3(-2.5f, 0.5f, 0.0f),
            1.2f,
            2.5f,
            AUTO_BULLET_DATA);

    public static final PhysicsBodyData RUST_PHYSICS_DATA = new PhysicsBodyData(
            1.0f,
            1.0f,
            true, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.DynamicBody,
            false, //not a sensor
            ENEMY_CATEGORY,
            (short) (/*PLAYER_CATEGORY |*/ TRIGGER_CATEGORY | BRICK_CATEGORY | AMMO_CATEGORY));

    public static final PhysicsBodyData BANGBANG_PHYSICS_DATA = new PhysicsBodyData(
            1.8f,
            1.8f,
            true, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.DynamicBody,
            false, //not a sensor
            ENEMY_CATEGORY,
            (short) (/*PLAYER_CATEGORY |*/ TRIGGER_CATEGORY | BRICK_CATEGORY | AMMO_CATEGORY));

    public static final PhysicsBodyData SPIKE_PHYSICS_DATA = new PhysicsBodyData(
            0.6f,
            0.6f,
            true, //if "true" then this is a circle shape - only width will be used
            0.0f,
            BodyDef.BodyType.DynamicBody,
            false, //not a sensor
            ENEMY_CATEGORY,
            (short) (/*PLAYER_CATEGORY |*/ TRIGGER_CATEGORY | BRICK_CATEGORY | AMMO_CATEGORY));

    public static final int ENEMY_WALK_STATE_INDEX = 0;
    public static final int ENEMY_JUMP_STATE_INDEX = 1;

    public static final EnemyData RUST_DATA         = new EnemyData(RUSTY_WALK_SEQ,
            RUSTY_JUMP_SEQ,
            RUST_PHYSICS_DATA,
            2.0f, 100.0f);

    public static final EnemyData BANGBANG_DATA     = new EnemyData(BANGBANG_WALK_SEQ,
            BANGBANG_JUMP_SEQ,
            BANGBANG_PHYSICS_DATA,
            1.5f, 200.0f);

    public static final EnemyData SPIKE_DATA     = new EnemyData(SPIKE_WALK_SEQ,
            SPIKE_JUMP_SEQ,
            SPIKE_PHYSICS_DATA,
            3.0f, 200.0f);

    public static final Color SPLASH_CLEAR_COLOR = new Color(0x77216FFF);
    public static final Color LOADING_CLEAR_COLOR = new Color(0x77216FFF);
    public static final Color GAME_CLEAR_COLOR = new Color(0f, 0.2f, 0f, 1f);

    public static final Vector3 LEFT_TOP = new Vector3(-1.6f, 49.6f, 0.0f);
    public static final float TILE_OFFSET = 3.2f;

    public static final Vector2 ENEMY_SPAWN_POSITION = new Vector2(44.8f, 49.6f);

    public static class SpawnTimeData {
        public float minTime, maxTime, offset;
        public int range, prob;

        public SpawnTimeData(float minTime, float maxTime, float offset, int mod, int prob) {
            this.minTime = minTime;
            this.maxTime = maxTime;
            this.offset = offset;
            this.range = mod;
            this.prob = prob;
        }
    }

    public static final SpawnTimeData RUST_TIME = new SpawnTimeData(3.0f, 4.0f, 0.3f, 5, 40);
    public static final SpawnTimeData BANGBANG_TIME = new SpawnTimeData(4.0f, 8.0f, 0.6f, 2, 60);
    public static final SpawnTimeData SPIKE_TIME = new SpawnTimeData(8.0f, 12.0f, 1.2f, 3, 80);

    public static final String BEACH_LEVEL_LAYOUT =
                    "2222222222222402222222222222\n" +
                    "2666666666660000666666666662\n" +
                    "2666636666660000666666666662\n" +
                    "2666666667111111117666666662\n" +
                    "2666666666666666666666666662\n" +
                    "2666666666666666666666666662\n" +
                    "2111111766666666666671111112\n" +
                    "2666666666666666666666666662\n" +
                    "2666666666666666666666666662\n" +
                    //"2666666666666666666666666662\n" +
                    "2666667111111111111117666662\n" +
                    "2666666666666666666666666662\n" +
                    "2666666666666666666666666662\n" +
                    "2111766667111111117666671112\n" +
                    "2666666666660000666666666662\n" +
                    "2666666666660000666666666662\n" +
                    "2111111111118508111111111112\n";
    //"2222222222222222222222222222";


    public String bombCrazeLevelDetail;
    public InitValues(){
        bombCrazeLevelDetail =
                        "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEERRRRRREEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEERRRRRRRREEEERRRRRRRREEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WRRRRRREEEERRRRRRRRRREEEERRRRRRW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEERRRRRRRRRREEEERRRRRRRRRREEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WREEEEEEEEERRRRRRRRRREEEEEEEEERW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEW\n" +
                        "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
    }

    public enum BGType{
        Parallax,
        Loiter
    }

    public static final String FISHING_BOAT = "fishingboat";
    public static final String WIND_SURFER  = "windsurfer";
    public static final String SHIP         = "ship";

    public static final String TRAIN_TRACK  = "traintrack";
    public static final String ROAD         = "road";
    public static final String LIGHT_HOUSE  = "lighthouse";
    public static final String SANTHOME     = "santhome";

    public static final String SKY          = "sky";
    public static final String SEA          = "sea";

    public static class BGElement{
        public String name;
        public BGType type;
        public float positionX, positionY, scaleX, scaleY;

        public BGElement(String name, BGType type,
                         float positionX, float positionY, float scaleX, float scaleY) {
            this.name = name;
            this.type = type;
            this.positionX = positionX;
            this.positionY = positionY;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
    }

    public static class ParallaxElement extends BGElement{
        public float offset;

        public ParallaxElement(String name, BGType type, float positionX, float positionY,
                               float scaleX, float scaleY, float offset) {

            super(name, type, positionX, positionY, scaleX, scaleY);
            this.offset = offset;
        }
    }

    public static final ParallaxElement SKY_PARALLAX = new ParallaxElement(SKY,
            BGType.Parallax,
            0.0f,
            15.0f,
            0.1f,
            0.05f,
            1.0f);

    public static final ParallaxElement SEA_PARALLAX = new ParallaxElement(SEA,
            BGType.Parallax,
            0.0f,
            0.0f,
            0.1f,
            0.05f,
            1.0f);

    public static final ParallaxElement SANTHOME_PARALLAX = new ParallaxElement(SANTHOME,
            BGType.Parallax,
            50.0f,
            7.0f,
            0.08f,
            0.08f,
            3.0f);

    public static final ParallaxElement LIGHT_HOUSE_PARALLAX = new ParallaxElement(LIGHT_HOUSE,
            BGType.Parallax,
            10.0f,
            8.0f,
            0.06f,
            0.06f,
            3.0f);

    public static final ParallaxElement TRAIN_TRACK_PARALLAX = new ParallaxElement(TRAIN_TRACK,
            BGType.Parallax,
            0.0f,
            0.0f,
            0.05f,
            0.05f,
            1.0f);

    public static final ParallaxElement ROAD_PARALLAX = new ParallaxElement(ROAD,
            BGType.Parallax,
            0.0f,
            0.0f,
            0.1f,
            0.05f,
            1.0f);

    public static class LoiteringElement extends BGElement {
        public float moveBy;
        public float minRestTime, moveDuration;

        public LoiteringElement(String name, BGType type, float positionX, float positionY,
                                float scaleX, float scaleY,
                                float moveBy, float minRestTime, float moveDuration) {

            super(name, type, positionX, positionY, scaleX, scaleY);
            this.moveBy = moveBy;
            this.minRestTime = minRestTime;
            this.moveDuration = moveDuration;
        }
    }

    public static final LoiteringElement FISHING_BOAT_LOITER = new LoiteringElement(
            FISHING_BOAT,
            BGType.Loiter,
            25.0f,
            13.0f,
            0.1f,
            0.1f,
            20.0f,
            15.0f,
            2.0f
    );

    public static final LoiteringElement WIND_SURFER_LOITER = new LoiteringElement(
            WIND_SURFER,
            BGType.Loiter,
            60.0f,
            13.0f,
            0.1f,
            0.1f,
            10.0f,
            5.0f,
            1.0f
    );

    public static final LoiteringElement SHIP_LOITER = new LoiteringElement(
            SHIP,
            BGType.Loiter,
            5.0f,
            15.0f,
            0.1f,
            0.1f,
            5.0f,
            10.0f,
            15.0f
    );

    public static final BGElement[] BEACH_LEVEL_BG_ELEMENTS = new BGElement[]{
            SKY_PARALLAX,
            SEA_PARALLAX,
            SHIP_LOITER,
            FISHING_BOAT_LOITER,
            WIND_SURFER_LOITER,
            SANTHOME_PARALLAX,
            LIGHT_HOUSE_PARALLAX,
            ROAD_PARALLAX,
            TRAIN_TRACK_PARALLAX
    };

    public static class LevelDetails{
        public String levelLayout;
        public BGElement[] elementsByOrderFromBehind;

        public LevelDetails(String levelLayout, BGElement[] elementsByOrderFromBehind) {
            this.levelLayout = levelLayout;
            this.elementsByOrderFromBehind = elementsByOrderFromBehind;
        }
    }

    public static final LevelDetails BEACH_LEVEL = new LevelDetails(
            BEACH_LEVEL_LAYOUT,
            BEACH_LEVEL_BG_ELEMENTS
    );
}

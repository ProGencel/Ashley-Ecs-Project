package com.progencel.progencel.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.progencel.progencel.factories.EntityFactory;
import com.progencel.progencel.physics.GameContactListener;
import com.progencel.progencel.systems.*;
import com.progencel.progencel.utils.Constants;
import com.progencel.progencel.utils.StaticBodyBuilder;
import com.progencel.progencel.utils.TiledMapReader;

public class GameScreen implements Screen {

    private Engine engine;
    private EntityFactory factory;
    private FitViewport viewport;
    private World world;
    private final AssetManager assetManager;
    private final Box2DDebugRenderer debugRenderer;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private final SpriteBatch batch = new SpriteBatch();

    public GameScreen(AssetManager assetManager) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(10,10,camera);
        this.mapRenderer = new OrthogonalTiledMapRenderer(assetManager.get("world/world.tmx"), Constants.UNIT_SCALE,batch);
        this.debugRenderer = new Box2DDebugRenderer();
        this.assetManager = assetManager;
    }

    @Override
    public void show() {

        TiledMapReader reader = new TiledMapReader(mapRenderer.getMap(),Constants.UNIT_SCALE);
        world = new World(new Vector2(0,0),true);
        world.setContactListener(new GameContactListener());
        engine = new Engine();
        factory = new EntityFactory(engine,world,assetManager.get("atlas/AshleyLearning.atlas"));

        engine.addSystem(new MovementSystem());
        engine.addSystem(new PhysicSystem(world));
        engine.addSystem(new PhysicSyncSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderSystem(batch));

        Vector2 spawn = reader.getPoint("spawns","player_spawn");

        StaticBodyBuilder walls = new StaticBodyBuilder(world);
        for(Rectangle r : reader.getRects("collisions"))
        {
            walls.addRect(r);
        }

        for(TiledMapReader.MapShape s : reader.getTileObjectShapes("object_decors"))
        {
            if(s.type == TiledMapReader.MapShape.Type.POLYGON)
            {
                walls.addPolygon(s.vertices);
            }
            else
            {
                walls.addRect(s.bounds);
            }
        }

        for(TiledMapReader.MapTileObject o : reader.getTileObjects("object_decors"))
        {
            factory.createProp(o.region,o.x + o.width/2,o.y);
        }

        for(TiledMapReader.MapTileObject o : reader.getTileObjects("sensor_layer"))
        {
            factory.createProp(o.region,o.x + o.width/2,o.y);
        }

        for(TiledMapReader.MapShape s : reader.getShapes("sensor_layer"))
        {
            if(s.type != TiledMapReader.MapShape.Type.ELLIPSE)
            {
                continue;
            }

            float rad = s.bounds.width/2;
            float cx = s.bounds.x + rad;
            float cy = s.bounds.y + s.bounds.height / 2f;
            String type = TiledMapReader.getString(s.properties, "type", "");

            walls.addSensorCircle(cx, cy, rad, type);
        }

        factory.createPlayer(spawn.x,spawn.y);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        mapRenderer.setView(camera);
        mapRenderer.render();

        engine.update(delta);

       // debugRenderer.render(world,camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }
}

package com.progencel.progencel.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.progencel.progencel.components.TextureComponent;
import com.progencel.progencel.components.TransformComponent;
import com.progencel.progencel.components.VelocityComponent;
import com.progencel.progencel.factories.EntityFactory;
import com.progencel.progencel.systems.*;
import com.progencel.progencel.utils.Constants;
import com.progencel.progencel.utils.TiledMapReader;

public class GameScreen implements Screen {

    private Engine engine;
    private EntityFactory factory;
    private FitViewport viewport;
    private World world;
    private final AssetManager assetManager;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private final SpriteBatch batch = new SpriteBatch();

    public GameScreen(AssetManager assetManager) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(10,10,camera);
        mapRenderer = new OrthogonalTiledMapRenderer(assetManager.get("world/world.tmx"), Constants.UNIT_SCALE,batch);
        this.assetManager = assetManager;
    }

    @Override
    public void show() {

        TiledMapReader reader = new TiledMapReader(mapRenderer.getMap(),Constants.UNIT_SCALE);
        world = new World(new Vector2(0,0),true);
        engine = new Engine();
        factory = new EntityFactory(engine,world,assetManager.get("atlas/AshleyLearning.atlas"));

        engine.addSystem(new AnimationSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PhysicSystem(world));
        engine.addSystem(new PhysicSyncSystem());
        engine.addSystem(new RenderSystem(batch));

        Vector2 spawn = reader.getPoint("spawns","player_spawn");

        factory.createPlayer(spawn.x,spawn.y);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        mapRenderer.setView(camera);
        mapRenderer.render();

        engine.update(delta);
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
    }
}

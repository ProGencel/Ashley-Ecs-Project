package com.progencel.progencel.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.progencel.progencel.components.TextureComponent;
import com.progencel.progencel.components.TransformComponent;
import com.progencel.progencel.components.VelocityComponent;
import com.progencel.progencel.factories.EntityFactory;
import com.progencel.progencel.systems.MovementSystem;
import com.progencel.progencel.systems.PhysicSyncSystem;
import com.progencel.progencel.systems.PhysicSystem;
import com.progencel.progencel.systems.RenderSystem;

public class GameScreen implements Screen {

    private Engine engine;
    private EntityFactory factory;
    private FitViewport viewport = new FitViewport(1280,720);
    private World world;

    private final SpriteBatch batch = new SpriteBatch();

    @Override
    public void show() {

        world = new World(new Vector2(0,-9.8f),true);
        engine = new Engine();
        factory = new EntityFactory(engine,world);

        engine.addSystem(new MovementSystem());
        engine.addSystem(new PhysicSystem(world));
        engine.addSystem(new PhysicSyncSystem());
        engine.addSystem(new RenderSystem(batch));

        factory.createPlayer(100,100);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    }
}

package com.progencel.progencel.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.progencel.progencel.components.TextureComponent;
import com.progencel.progencel.components.TransformComponent;
import com.progencel.progencel.components.VelocityComponent;
import com.progencel.progencel.factories.EntityFactory;
import com.progencel.progencel.systems.MovementSystem;
import com.progencel.progencel.systems.RenderSystem;

public class GameScreen implements Screen {

    private Engine engine;
    private EntityFactory factory;
    private FitViewport viewport = new FitViewport(1280,720);

    private final SpriteBatch batch = new SpriteBatch();

    @Override
    public void show() {

        engine = new Engine();
        factory = new EntityFactory(engine);

        engine.addSystem(new MovementSystem());
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

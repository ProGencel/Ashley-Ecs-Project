package com.progencel.progencel.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.progencel.progencel.components.TextureComponent;
import com.progencel.progencel.components.TransformComponent;
import com.progencel.progencel.utils.Constants;

public class RenderSystem extends IteratingSystem {

    private final ComponentMapper<TextureComponent> tex = ComponentMapper.getFor(TextureComponent.class);
    private final ComponentMapper<TransformComponent> trans = ComponentMapper.getFor(TransformComponent.class);

    private final SpriteBatch batch;

    public RenderSystem(SpriteBatch batch) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureRegion r = tex.get(entity).texture;
        float w = r.getRegionWidth() * Constants.UNIT_SCALE;
        float h = r.getRegionHeight() * Constants.UNIT_SCALE;

        TransformComponent t = trans.get(entity);
        batch.draw(r, t.x - w / 2f, t.y, w, h);
    }
}

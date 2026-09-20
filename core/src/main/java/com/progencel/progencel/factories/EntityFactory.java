package com.progencel.progencel.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.progencel.progencel.components.TextureComponent;
import com.progencel.progencel.components.TransformComponent;
import com.progencel.progencel.components.VelocityComponent;

public class EntityFactory {

    private final Engine engine;

    public EntityFactory(Engine engine)
    {
        this.engine = engine;
    }

    public Entity createPlayer(float x, float y)
    {
        Entity e = engine.createEntity();

        TransformComponent t = engine.createComponent(TransformComponent.class);
        t.x = x;
        t.y = y;
        e.add(t);

        TextureComponent tex = engine.createComponent(TextureComponent.class);
        tex.texture = new TextureRegion(new Texture("char.png"));
        e.add(tex);

        e.add(engine.createComponent(VelocityComponent.class));

        engine.addEntity(e);
        return e;
    }

}

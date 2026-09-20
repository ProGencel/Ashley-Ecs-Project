package com.progencel.progencel.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.progencel.progencel.components.BodyComponent;
import com.progencel.progencel.components.TextureComponent;
import com.progencel.progencel.components.TransformComponent;
import com.progencel.progencel.components.VelocityComponent;

public class EntityFactory {

    private final Engine engine;
    private final World world;

    public EntityFactory(Engine engine, World world)
    {
        this.engine = engine;
        this.world = world;
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

        BodyComponent b = engine.createComponent(BodyComponent.class);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100,100);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b.body = world.createBody(bodyDef);
        e.add(b);

        e.add(engine.createComponent(VelocityComponent.class));

        engine.addEntity(e);
        return e;
    }

}

package com.progencel.progencel.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.progencel.progencel.components.*;

public class EntityFactory {

    private static final float IDLE_DURATION = 0.2f;
    private static final float RUN_DURATION = 0.1f;

    private final Engine engine;
    private final World world;
    private final TextureAtlas atlas;

    public EntityFactory(Engine engine, World world, TextureAtlas atlas)
    {
        this.engine = engine;
        this.world = world;
        this.atlas = atlas;
    }

    public Entity createProp(TextureRegion texture, float x, float y)
    {
        Entity e = engine.createEntity();

        TransformComponent tc = engine.createComponent(TransformComponent.class);
        tc.x = x;
        tc.y = y;

        TextureComponent tec = engine.createComponent(TextureComponent.class);
        tec.texture = texture;

        e.add(tc);
        e.add(tec);
        engine.addEntity(e);
        return e;
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
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.35f);
        shape.setPosition(new Vector2(0, 0.35f));

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;

        b.body.createFixture(fdef);
        e.add(b);

        AnimationComponent ac = engine.createComponent(AnimationComponent.class);
        setAnims(ac,"adam");
        e.add(ac);

        e.add(engine.createComponent(VelocityComponent.class));
        e.add(engine.createComponent(StateComponent.class));
        e.add(engine.createComponent(TextureComponent.class));

        engine.addEntity(e);
        return e;
    }

    private void setAnims(AnimationComponent ac, String name)
    {
        Array<TextureAtlas.AtlasRegion> rightIdle = atlas.findRegions(name + "_idle_right");
        Array<TextureAtlas.AtlasRegion> leftIdle  = atlas.findRegions(name + "_idle_left");
        Array<TextureAtlas.AtlasRegion> upIdle    = atlas.findRegions(name + "_idle_up");
        Array<TextureAtlas.AtlasRegion> downIdle  = atlas.findRegions(name + "_idle_down");
        Array<TextureAtlas.AtlasRegion> rightRun  = atlas.findRegions(name + "_run_right");
        Array<TextureAtlas.AtlasRegion> leftRun   = atlas.findRegions(name + "_run_left");
        Array<TextureAtlas.AtlasRegion> upRun     = atlas.findRegions(name + "_run_up");
        Array<TextureAtlas.AtlasRegion> downRun   = atlas.findRegions(name + "_run_down");

        ac.rightIdleAnim = new Animation<>(IDLE_DURATION, rightIdle, Animation.PlayMode.LOOP);
        ac.leftIdleAnim  = new Animation<>(IDLE_DURATION, leftIdle,  Animation.PlayMode.LOOP);
        ac.upIdleAnim    = new Animation<>(IDLE_DURATION, upIdle,    Animation.PlayMode.LOOP);
        ac.downIdleAnim  = new Animation<>(IDLE_DURATION, downIdle,  Animation.PlayMode.LOOP);

        ac.rightRunAnim = new Animation<>(RUN_DURATION, rightRun, Animation.PlayMode.LOOP);
        ac.leftRunAnim  = new Animation<>(RUN_DURATION, leftRun,  Animation.PlayMode.LOOP);
        ac.upRunAnim    = new Animation<>(RUN_DURATION, upRun,    Animation.PlayMode.LOOP);
        ac.downRunAnim  = new Animation<>(RUN_DURATION, downRun,  Animation.PlayMode.LOOP);
    }

}

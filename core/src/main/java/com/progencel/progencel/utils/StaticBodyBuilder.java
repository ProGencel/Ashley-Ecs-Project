package com.progencel.progencel.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class StaticBodyBuilder {

    private final Body body;

    public StaticBodyBuilder(World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);   // (0,0)'da duran tek body
    }

    public void addRect(Rectangle r) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(r.width / 2f, r.height / 2f,
            new Vector2(r.x + r.width / 2f, r.y + r.height / 2f), 0f);
        attach(shape);
    }

    public void addCircle(float x, float y, float radius) {
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(x, y));
        shape.setRadius(radius);
        attach(shape);
    }

    /** x,y,x,y... formatında. Dışbükey ve en fazla 8 köşe olmalı. */
    public void addPolygon(float[] vertices) {
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        attach(shape);
    }

    private void attach(Shape shape) {
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.friction = 0f;   // top-down'da duvara sürtünüp takılmasın
        body.createFixture(fd);
        shape.dispose();    // fixture kopyaladı, orijinali temizle
    }
}

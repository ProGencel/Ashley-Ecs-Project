package com.progencel.progencel.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.progencel.progencel.components.BodyComponent;
import com.progencel.progencel.components.TransformComponent;

public class PhysicSyncSystem extends IteratingSystem {

    private final ComponentMapper<BodyComponent> b = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<TransformComponent> t = ComponentMapper.getFor(TransformComponent.class);

    public PhysicSyncSystem() {
        super(Family.all(BodyComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent b = this.b.get(entity);
        TransformComponent t = this.t.get(entity);

        t.x = b.body.getPosition().x;
        t.y = b.body.getPosition().y;
    }
}

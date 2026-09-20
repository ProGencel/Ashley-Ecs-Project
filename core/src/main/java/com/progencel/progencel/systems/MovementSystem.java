package com.progencel.progencel.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.progencel.progencel.components.TransformComponent;
import com.progencel.progencel.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem()
    {
        super(Family.all(TransformComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent t = tm.get(entity);
        VelocityComponent v = vm.get(entity);

        v.dy = 0;
        v.dx = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            v.dy = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            v.dy = -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            v.dx = -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            v.dx = 1;
        }

        t.x += v.dx;;
        t.y += v.dy;
    }
}

package com.progencel.progencel.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.progencel.progencel.components.BodyComponent;
import com.progencel.progencel.components.StateComponent;
import com.progencel.progencel.components.VelocityComponent;
import com.progencel.progencel.enums.CharacterState;
import com.progencel.progencel.enums.Direction;

public class MovementSystem extends IteratingSystem {

    private final ComponentMapper<BodyComponent> tm = ComponentMapper.getFor(BodyComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<StateComponent> sm = ComponentMapper.getFor(StateComponent.class);

    public MovementSystem()
    {
        super(Family.all(BodyComponent.class, VelocityComponent.class, StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent t = tm.get(entity);
        VelocityComponent v = vm.get(entity);
        StateComponent s = sm.get(entity);

        v.dy = 0;
        v.dx = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            v.dy = 1;
            s.direction = Direction.UP;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            v.dy = -1;
            s.direction = Direction.DOWN;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            v.dx = -1;
            s.direction = Direction.LEFT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            v.dx = 1;
            s.direction = Direction.RIGHT;
        }

        if(v.dx == 0 && v.dy == 0)
        {
            s.set(CharacterState.IDLE);
        }
        else
        {
            s.set(CharacterState.RUN);
        }

        t.body.setLinearVelocity(v.dx*2,v.dy*2);
    }
}

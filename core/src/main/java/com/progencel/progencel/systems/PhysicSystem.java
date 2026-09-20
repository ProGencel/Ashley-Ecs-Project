package com.progencel.progencel.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicSystem extends EntitySystem {

    float accumulator = 0f;
    private final World world;

    public PhysicSystem(World world) {
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        doPhysicsStep(deltaTime);
    }

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f,6,2);
            accumulator -= 1/60f;
        }
    }
}

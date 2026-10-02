package com.progencel.progencel.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        Fixture sensor = a.isSensor() ? a : (b.isSensor() ? b : null);
        if (sensor == null) return;

        Fixture other = (sensor == a) ? b : a;
        if (other.getBody().getUserData() instanceof Entity) {
            System.out.println("Sensore girildi: " + sensor.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

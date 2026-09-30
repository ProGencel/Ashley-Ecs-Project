package com.progencel.progencel.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.progencel.progencel.components.AnimationComponent;
import com.progencel.progencel.components.StateComponent;
import com.progencel.progencel.components.TextureComponent;
import com.progencel.progencel.enums.CharacterState;

public class AnimationSystem extends IteratingSystem {

    private final ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
    private final ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    private final ComponentMapper<StateComponent> sm = ComponentMapper.getFor(StateComponent.class);

    public AnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class, StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StateComponent stateComponent = sm.get(entity);
        Animation<TextureRegion> anim = pick(am.get(entity), stateComponent);

        if(anim != null)
        {
            tm.get(entity).texture = anim.getKeyFrame(stateComponent.time);
        }
        stateComponent.time += deltaTime;
    }

    private Animation<TextureRegion> pick(AnimationComponent a, StateComponent s) {
        if (s.get() == CharacterState.RUN) {
            return switch (s.direction) {
                case RIGHT -> a.rightRunAnim;
                case LEFT -> a.leftRunAnim;
                case UP -> a.upRunAnim;
                default -> a.downRunAnim;
            };
        }
        return switch (s.direction) {
            case RIGHT -> a.rightIdleAnim;
            case LEFT -> a.leftIdleAnim;
            case UP -> a.upIdleAnim;
            default -> a.downIdleAnim;
        };
    }
}

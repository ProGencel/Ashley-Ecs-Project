package com.progencel.progencel.components;

import com.badlogic.ashley.core.Component;
import com.progencel.progencel.enums.CharacterState;
import com.progencel.progencel.enums.Direction;

public class StateComponent implements Component {

    private CharacterState state = CharacterState.IDLE;
    public Direction direction = Direction.DOWN;
    public float time = 0f;

    public CharacterState get()
    {
        return state;
    }

    public void set(CharacterState newState)
    {
        if(state != newState)
        {
            state = newState;
            time = 0f;
        }
    }

}

package com.progencel.progencel.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {

    public Animation<TextureRegion> rightIdleAnim, leftIdleAnim, upIdleAnim, downIdleAnim;
    public Animation<TextureRegion> rightRunAnim, leftRunAnim, upRunAnim, downRunAnim;

}

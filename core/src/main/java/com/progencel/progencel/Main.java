package com.progencel.progencel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.progencel.progencel.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private AssetManager assetManager;

    @Override
    public void create() {
        this.assetManager = new AssetManager();
        loadAssets();
        setScreen(new GameScreen(assetManager.get("atlas/AshleyLearning.atlas")));
    }

    private void loadAssets()
    {
        assetManager.load("atlas/AshleyLearning.atlas", TextureAtlas.class);
        assetManager.finishLoading();
    }
}

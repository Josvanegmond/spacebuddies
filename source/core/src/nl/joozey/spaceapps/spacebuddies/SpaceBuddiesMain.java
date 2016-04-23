package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.Queue;

public class SpaceBuddiesMain extends ApplicationAdapter {

    private static final String TAG = SpaceBuddiesMain.class.getName();

    private SpriteBatch batch;
    private Screen _currentScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();

        showScreen(Constants.FIND_MOON_SCREEN);
    }

    public void showScreen(Screen screen) {
        screen.create(batch);
        _currentScreen = screen;
    }

    @Override
    public void render() {
        _currentScreen.render(batch);
    }
}

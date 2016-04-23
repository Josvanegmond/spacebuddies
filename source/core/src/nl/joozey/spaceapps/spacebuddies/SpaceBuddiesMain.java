package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        Gdx.input.setInputProcessor(screen);
        screen.create(this);
        _currentScreen = screen;
    }

    @Override
    public void render() {
        _currentScreen.render(batch);
    }
}

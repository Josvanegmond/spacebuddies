package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by mint on 23-4-16.
 */
public class GoToMoonScreen implements Screen {

    private static final String TAG = GoToMoonScreen.class.getName();

    private Texture backgroundImage;
    private Texture moonImage;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create(SpaceBuddiesMain main) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        backgroundImage = new Texture("starfield.jpg");
        backgroundImage.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        moonImage = new Texture("moon.png");
    }

    @Override
    public void render(Batch batch) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

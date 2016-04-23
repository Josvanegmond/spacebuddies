package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mint on 23-4-16.
 */
public class GoToMoonScreen implements Screen {

    private static final String TAG = GoToMoonScreen.class.getName();

    private Texture backgroundImage;
    private Texture moonImage;
    private Texture earthImage;
    private Texture rocketImage;
    private ShapeRenderer shapeRenderer;

    private Vector2 moonSize;
    private Vector2 earthSize;
    private Vector2 rocketSize;
    private Vector2 rocketLocation;
    private Vector2 rocketVelocity;

    private boolean dragRocket;

    @Override
    public void create(SpaceBuddiesMain main) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        backgroundImage = new Texture("starfield.jpg");
        backgroundImage.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        moonImage = new Texture("moon.png");
        earthImage = new Texture("earth.png");
        rocketImage = new Texture("rocket.png");

        moonSize = new Vector2(
                moonImage.getWidth() * .01f * Gdx.graphics.getDensity(),
                moonImage.getHeight() * .01f * Gdx.graphics.getDensity());

        earthSize = new Vector2(
                earthImage.getWidth() * .06f * Gdx.graphics.getDensity(),
                earthImage.getHeight() * .06f * Gdx.graphics.getDensity());

        rocketSize = new Vector2(
                rocketImage.getWidth() * .03f * Gdx.graphics.getDensity(),
                rocketImage.getHeight() * .03f * Gdx.graphics.getDensity());

        rocketLocation = new Vector2(
                Gdx.graphics.getWidth() * .2f + earthSize.x * .25f,
                Gdx.graphics.getHeight() * .2f + earthSize.y * .25f);
    }

    @Override
    public void render(Batch batch) {

        if (dragRocket && rocketVelocity != null) {
            if (rocketVelocity.len() > 0) {
            }
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(
                moonImage,
                Gdx.graphics.getWidth() * .66f - moonSize.x * .5f,
                Gdx.graphics.getHeight() * .66f - moonSize.y * .5f,
                moonSize.x, moonSize.y);

        batch.draw(
                earthImage,
                Gdx.graphics.getWidth() * .2f - earthSize.x * .5f,
                Gdx.graphics.getHeight() * .2f - earthSize.y * .5f,
                earthSize.x, earthSize.y);

        batch.draw(
                rocketImage,
                rocketLocation.x, rocketLocation.y,
                rocketSize.x, rocketSize.y);

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
        if (Math.abs(screenX - rocketLocation.x) < rocketSize.x &&
                Math.abs(screenY - rocketLocation.y) < rocketSize.y) {
            dragRocket = true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (dragRocket) {
            dragRocket = false;
            rocketVelocity = new Vector2(rocketLocation.x - screenX, rocketLocation.y - screenY);
        }
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

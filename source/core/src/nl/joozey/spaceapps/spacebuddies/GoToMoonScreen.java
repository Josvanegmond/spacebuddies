package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mint on 23-4-16.
 */
public class GoToMoonScreen implements Screen {

    private static final String TAG = GoToMoonScreen.class.getName();

    private Texture backgroundImage;
    private Texture moonImage;
    private Texture earthImage;
    private Texture rocketImage;
    private Texture paxi;
    private Texture[] paxiMouth = new Texture[3];
    private ShapeRenderer shapeRenderer;

    private Vector2 moonSize;
    private Vector2 earthSize;
    private Vector2 rocketSize;
    private Vector2 rocketLocation;
    private Vector2 moonLocation;
    private Vector2 touchVector;
    private Vector2 rocketVelocity;
    private Vector2 paxiSize;

    private boolean dragRocket;
    private boolean shootRocket;

    private float speed = 60f;
    private float counter = 0;

    private List<Rectangle> lineList;

    private SpaceBuddiesMain main;
    private BitmapFont font;

    @Override
    public void create(SpaceBuddiesMain main) {
        font = new BitmapFont();
        font.getData().setScale(3);
        this.main = main;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        backgroundImage = new Texture("starfield.jpg");
        backgroundImage.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        moonImage = new Texture("moon.png");
        earthImage = new Texture("earth.png");
        rocketImage = new Texture("rocket.png");

        paxiMouth[0] = new Texture("paxi-01.png");
        paxiMouth[1] = new Texture("paxi-02.png");
        paxiMouth[2] = new Texture("paxi-03.png");
        paxi = paxiMouth[0];

        lineList = new ArrayList<Rectangle>();

        moonSize = new Vector2(
                moonImage.getWidth() * .1f * Gdx.graphics.getDensity(),
                moonImage.getHeight() * .1f * Gdx.graphics.getDensity());

        moonLocation = new Vector2(
                Gdx.graphics.getWidth() * .66f - moonSize.x * .5f,
                Gdx.graphics.getHeight() * .66f - moonSize.y * .5f);

        earthSize = new Vector2(
                earthImage.getWidth() * .6f * Gdx.graphics.getDensity(),
                earthImage.getHeight() * .6f * Gdx.graphics.getDensity());

        rocketSize = new Vector2(
                rocketImage.getWidth() * .3f * Gdx.graphics.getDensity(),
                rocketImage.getHeight() * .3f * Gdx.graphics.getDensity());

        paxiSize = new Vector2(
                paxi.getWidth() * .3f * Gdx.graphics.getDensity(),
                paxi.getHeight() * .3f * Gdx.graphics.getDensity());

        rocketLocation = new Vector2(
                Gdx.graphics.getWidth() * .2f + earthSize.x * .25f,
                Gdx.graphics.getHeight() * .2f + earthSize.y * .25f);
    }

    @Override
    public void render(Batch batch) {

        //paxi mouth
        if(counter > 1) {
            counter = 0;
            paxi = paxiMouth[(int)(Math.random()*paxiMouth.length)];
        }
        counter += Gdx.graphics.getDeltaTime() * 10f;

        if (touchVector != null) {
            rocketVelocity = new Vector2(rocketLocation.x - touchVector.x, rocketLocation.y - touchVector.y);
        }

        //move the rocket
        if (touchVector != null && shootRocket) {
            if (touchVector.len() > 0) {
                rocketLocation.x += rocketVelocity.nor().x * speed * Gdx.graphics.getDeltaTime();
                rocketLocation.y += rocketVelocity.nor().y * speed * Gdx.graphics.getDeltaTime();

                if (rocketLocation.len() > moonLocation.len()) {
                    main.showScreen(Constants.ORBIT_MOON_SCREEN);
                }
            } else {
                touchVector = null;
            }
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(
                moonImage,
                moonLocation.x, moonLocation.y,
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

        batch.draw(paxi, Gdx.graphics.getWidth() - paxiSize.x, 0, paxiSize.x, paxiSize.y);

        batch.end();

        Gdx.gl20.glLineWidth(4 * Gdx.graphics.getDensity());
        batch.begin();
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        if (!dragRocket && !shootRocket) {
            lineList.clear();
            _addDottedCircle(rocketLocation.x + rocketSize.x * .5f, rocketLocation.y + rocketSize.y * .5f,
                    rocketSize.x + 20);

        } else if (rocketVelocity != null && touchVector != null && dragRocket) {
            lineList.clear();
            _addDottedCircle(touchVector.x, touchVector.y,
                    rocketSize.x + 20);

            Vector2 dir = rocketVelocity.nor();
            float rocketEarthDist = moonLocation.cpy().dst(rocketLocation);
            float angle = Math.abs(rocketLocation.cpy().sub(touchVector).angle() - moonLocation.cpy().sub(moonSize.cpy().scl(.5f)).angle());
            if (angle < 5) {
                _addDottedLine(rocketLocation.x, rocketLocation.y,
                        moonLocation.x + dir.x * rocketEarthDist, moonLocation.y + dir.y * rocketEarthDist);

            } else if (angle >= 5 && angle < 15) {
                Vector2 endPos = new Vector2(rocketLocation.x + dir.x * rocketEarthDist, rocketLocation.y + dir.y * rocketEarthDist);
                _addDottedLine(rocketLocation.x, rocketLocation.y, endPos.x, endPos.y);
                _addDottedCircle(moonLocation.x + moonSize.x * .5f, moonLocation.y + moonSize.y * .5f,
                        endPos.dst(moonLocation.cpy().sub(moonSize.cpy().scl(.5f))));

            } else {
                _addDottedLine(rocketLocation.x, rocketLocation.y,
                        rocketLocation.x + dir.x * Gdx.graphics.getWidth(), rocketLocation.y + dir.y * Gdx.graphics.getWidth());
            }

        } else if (rocketVelocity != null && shootRocket) {

        }

        for (Rectangle line : lineList) {
            shapeRenderer.line(line.x, line.y, line.width, line.height);
        }

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(.36f, .64f, 0f, 1f));
        shapeRenderer.rect(Gdx.graphics.getWidth() - paxiSize.x - 500, paxiSize.y, 620, 200);
        shapeRenderer.setColor(new Color(1f, 1f, 1f, 1f));

        shapeRenderer.end();
        batch.end();

        batch.begin();
        font.draw(batch, "Let's go to the moon! \n\nPull on the rocket to get going!", Gdx.graphics.getWidth() - paxiSize.x - 480, paxiSize.y + 180);
        batch.end();

        Gdx.gl20.glLineWidth(1);
    }

    private void _addDottedCircle(float x, float y, float radius) {
        for (int i = 0; i < 360; i += 16) {
            float lx = x + (float) Math.cos(Math.toRadians(i)) * radius;
            float ly = y + (float) Math.sin(Math.toRadians(i)) * radius;
            float lw = x + (float) Math.cos(Math.toRadians(i + 8)) * radius;
            float lh = y + (float) Math.sin(Math.toRadians(i + 8)) * radius;
            Rectangle line = new Rectangle(lx, ly, lw, lh);
            lineList.add(line);
        }
    }

    private void _addDottedLine(float x, float y, float w, float h) {
        Vector2 vec2 = new Vector2(w, h).sub(new Vector2(x, y));
        float length = vec2.len();
        for (int i = 0; i < length; i += 32) {
            vec2.clamp(length - i, length - i);
            Vector2 next = vec2.cpy().clamp(length - i + 16, length - i + 16);
            lineList.add(new Rectangle(x + vec2.x, y + vec2.y, x + next.x, y + next.y));
        }
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
        if (!dragRocket && !shootRocket &&
                Math.abs(screenX - rocketLocation.x) < rocketSize.x &&
                Math.abs((Gdx.graphics.getHeight() - screenY) - rocketLocation.y) < rocketSize.y) {
            dragRocket = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (dragRocket) {
            dragRocket = false;
            shootRocket = true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (dragRocket && !shootRocket) {
            touchVector = new Vector2(screenX, (Gdx.graphics.getHeight() - screenY));
        }
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

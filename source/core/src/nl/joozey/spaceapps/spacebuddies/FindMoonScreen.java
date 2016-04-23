package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by mint on 23-4-16.
 */
public class FindMoonScreen implements Screen {

    private static final String TAG = FindMoonScreen.class.getName();

    private Texture img;
    private Texture moonImage;
    private BitmapFont font;

    private Vector2 moonVector;

    private boolean compassAvail;
    private double latitude;
    private double longitude;

    private float azimuth;
    private float pitch;
    private float roll;

    private ShapeRenderer shapeRenderer;

    private Queue<Vector2> translateQueue = new LinkedList<Vector2>();

    @Override
    public void create(Batch batch) {
        font = new BitmapFont();
        font.getData().setScale(3);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        latitude = 52.213952;
        longitude = 4.3263;
        compassAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);

        img = new Texture("starfield.jpg");
        img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        moonImage = new Texture("moon.png");

        boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        System.out.println(TAG + " Accelerometer? " + available);

        moonVector = XmlMoonParser.getMoonData();
        if (moonVector != null) {
            System.out.println(TAG + " MoonVector x:" + moonVector.x + " y:" + moonVector.y);
        } else {
            System.out.println(TAG + "MoonVector Null");
        }
    }

    @Override
    public void render(Batch batch) {
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        float accelZ = Gdx.input.getAccelerometerZ();

        float R = (float) Math.sqrt((accelX * accelX) + (accelY * accelY) + (accelZ * accelZ));

        float angleX = (float) Math.acos((double) accelX / R);
        float angleY = (float) Math.acos((double) accelY / R);
        float angleZ = (float) Math.acos((double) accelZ / R);

        angleX = (float) Math.toDegrees((double) angleX);
        angleY = (float) Math.toDegrees((double) angleY);
        angleZ = (float) Math.toDegrees((double) angleZ);

        azimuth = Gdx.input.getAzimuth();
        pitch = Gdx.input.getPitch();
        roll = Gdx.input.getRoll();

        float translateWidth = img.getWidth();
        float translateHeight = img.getHeight();
        int translateX = (int) ((azimuth + 180f) * translateWidth / 360f);
        int translateY = (int) (((angleX * ((angleZ < 90f) ? -1f : 1f)) + 90f) * translateHeight / 180f);

        translateQueue.add(new Vector2(translateX, translateY));
        if (translateQueue.size() > 10) {
            translateQueue.poll();
        }
        int smoothTranslateX = 0;
        int smoothTranslateY = 0;
        float signX = Math.signum(translateX);
        float signY = Math.signum(translateY);
        for (Vector2 v : translateQueue) {
            smoothTranslateX += Math.abs(v.x) * signX;
            smoothTranslateY += Math.abs(v.y) * signY;
        }

        smoothTranslateX /= translateQueue.size();
        smoothTranslateY /= translateQueue.size();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, 0, 0, smoothTranslateX, -smoothTranslateY, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(Color.WHITE);
        font.draw(batch, "Translate X:" + translateX, 20, 210);
        font.draw(batch, "Y:" + translateY, 600, 210);

        if (moonVector != null) {
            font.draw(batch, "Moon Altitude:" + moonVector.x, 20, 160);
            font.draw(batch, "Azimuth:" + moonVector.y, 600, 160);
        }

        font.draw(batch, "Azimuth X:" + azimuth, 20, 110);
        font.draw(batch, "Y:" + pitch, 600, 110);
        font.draw(batch, "Z:" + roll, 1180, 110);

        font.draw(batch, "Angle X:" + angleX, 20, 60);
        font.draw(batch, "Y:" + angleY, 600, 60);
        font.draw(batch, "Z:" + angleZ, 1180, 60);

        if (moonVector != null) {
            int moonX = (int) (((moonVector.x + 270f) / 360f) % 360f * img.getWidth());
            int moonY = (int) (((moonVector.y + 270f) / 180f) % 360f * img.getHeight());
            batch.draw(moonImage, moonX - smoothTranslateX, moonY - smoothTranslateY);
        }

        batch.end();
    }
}

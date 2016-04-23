package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpaceBuddiesMain extends ApplicationAdapter {

    private static final String TAG = SpaceBuddiesMain.class.getName();

    private SpriteBatch batch;
    private Texture img;
    private BitmapFont font;

    private Vector2 moonVector;

    private boolean compassAvail;
    private double latitude;
    private double longitude;

    private float azimuth;
    private float pitch;
    private float roll;

    @Override
    public void create() {
        font = new BitmapFont();
        font.getData().setScale(3);

        latitude = 52.213952;
        longitude = 4.3263;
        compassAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);

        batch = new SpriteBatch();
        img = new Texture("space.png");
        img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

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
    public void render() {
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

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(img, 0, 0);

        font.setColor(Color.WHITE);

        font.draw(batch, "Angle X:" + angleX, 20, 60);
        font.draw(batch, "Y:" + angleY, 600, 60);
        font.draw(batch, "Z:" + angleZ, 1180, 60);

        font.draw(batch, "Azimuth X:" + azimuth, 20, 110);
        font.draw(batch, "Y:" + pitch, 600, 110);
        font.draw(batch, "Z:" + roll, 1180, 110);

        if (moonVector != null) {
            font.draw(batch, " Moon Elevation:" + moonVector.x, 20, 160);
            font.draw(batch, "Azimuth:" + moonVector.y, 600, 160);
        }

        batch.end();


    }
}

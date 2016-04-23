package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sun.rmi.runtime.Log;

public class SpaceBuddiesMain extends ApplicationAdapter {

    private static final String TAG = SpaceBuddiesMain.class.getName();

    private SpriteBatch batch;
    private Texture img;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("space.png");
        img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        System.out.println(TAG + " Accelerometer? " + available);
    }

    @Override
    public void render() {
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        float accelZ = Gdx.input.getAccelerometerZ();

        float R = (float)Math.sqrt((accelX*accelX) + (accelY*accelY) + (accelZ*accelZ));

        float angleX = accelX/R;
        float angleY = accelY/R;
        float angleZ = accelZ/R;

        System.out.println(TAG + " Angle X:" + angleX + " Y:" + angleY + " Z:" + angleZ);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }
}

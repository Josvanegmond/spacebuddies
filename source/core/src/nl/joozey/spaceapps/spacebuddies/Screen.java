package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by mint on 23-4-16.
 */
public interface Screen {
    void render(Batch batch);
    void create(Batch batch);
}

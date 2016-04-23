package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by mint on 23-4-16.
 */
public interface Screen extends InputProcessor {
    void create(SpaceBuddiesMain main);
    void render(Batch batch);
}

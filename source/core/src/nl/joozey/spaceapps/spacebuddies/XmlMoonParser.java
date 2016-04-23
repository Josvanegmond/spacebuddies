package nl.joozey.spaceapps.spacebuddies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by mint on 23-4-16.
 */
public class XmlMoonParser {
    public static Vector2 getMoonData() {
        XmlReader xmlReader = new XmlReader();

        try {
            URL url = new URL("http://api.wolframalpha.com/v2/query?appid=2Q6RQG-58PVTW6AH6&input=position%20of%20moon%20in%20sky%20from%2052.213952°N%2C%204.3263°E&format=plaintext&podstate=SkyMap:PlanetaryMoonData__Show+decimal");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String xml = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                xml += inputLine;
            }
            in.close();

            Vector2 moonVector = new Vector2();

            XmlReader.Element xmlElement = xmlReader.parse(xml);
            Array<XmlReader.Element> children = xmlElement.getChildrenByNameRecursively("plaintext");

            if (children.size < 4) {
                return null;
            }

            String moonData = children.get(3).getText();
            moonVector.x = Float.parseFloat(moonData.substring(moonData.indexOf(" | ") + 3, moonData.indexOf("°")));
            moonData = moonData.substring(moonData.indexOf("azimuth | ") + 10);
            moonVector.y = Float.parseFloat(moonData.substring(0, moonData.indexOf("°")));
            return moonVector;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}

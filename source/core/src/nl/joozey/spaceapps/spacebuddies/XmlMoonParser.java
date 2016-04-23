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
            URL oracle = new URL("http://api.wolframalpha.com/v2/query?appid=2Q6RQG-58PVTW6AH6&input=position%20of%20moon%20in%20sky%20from%2052.213952°N%2C%204.3263°E&format=plaintext&podstate=Location:PlanetaryMoonData__Show+decimal");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String xml = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                xml += inputLine;
            }
            in.close();

            XmlReader.Element xmlElement = xmlReader.parse(xml);
            Array<XmlReader.Element> children = xmlElement.getChildrenByNameRecursively("plaintext");
            String moonData = children.get(1).getText();
            moonData = moonData.replace("right ascension | ", "");
            moonData = moonData.replace("^hdeclination |", "");
            moonData = moonData.replace("°", "");

            String[] moonDataSplit = moonData.split(" ");
            Vector2 moonVector = new Vector2(
                    Float.parseFloat(moonDataSplit[0]),
                    Float.parseFloat(moonDataSplit[1])
            );

            return moonVector;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}

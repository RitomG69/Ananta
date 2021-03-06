package me.ritomg.ananta.config;

import com.google.gson.*;
import com.lukflug.panelstudio.config.IConfigList;
import com.lukflug.panelstudio.config.IPanelConfig;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AnantaGuiConfig implements IConfigList {
    
    private JsonObject panelObject;
    private boolean hud;

    public AnantaGuiConfig(boolean hud) {
        this.hud = hud;
    }

    @Override
    public void begin(boolean loading) {
        if (loading) {
            if (!Files.exists(Paths.get(hud?"Ananta/Main/" + "ClickGUI" + ".json" : "Ananta/Main/" + "HudEditor" + ".json"))) {
                return;
            }
            try {
                InputStream inputStream;
                inputStream = Files.newInputStream(Paths.get(hud?"Ananta/Main/" + "ClickGUI" + ".json" : "Ananta/Main/" + "HudEditor" + ".json"));
                JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
                if (mainObject.get("Panels") == null) {
                    return;
                }
                panelObject = mainObject.get("Panels").getAsJsonObject();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!loading){
            panelObject = new JsonObject();
        }
    }

    @Override
    public void end(boolean loading) {
        if (panelObject == null) return;
        if (loading) {
            //see begin method
        }

        else if (!loading) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(hud?"Ananta/Main/" + "ClickGUI" + ".json" : "Ananta/Main/" + "HudEditor" + ".json"), StandardCharsets.UTF_8);
                JsonObject mainObject = new JsonObject();
                mainObject.add("Panels", panelObject);
                String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
                fileOutputStreamWriter.write(jsonString);
                fileOutputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        panelObject = null;
    }

    @Override
    public IPanelConfig addPanel(String title) {
        if (panelObject == null) return null;
        JsonObject valueObject = new JsonObject();
        panelObject.add(title, valueObject);
        return new APanelConfig(valueObject);
    }

    @Override
    public IPanelConfig getPanel(String title) {
        if (panelObject == null) return null;
        JsonElement configObject = panelObject.get(title);
        if (configObject != null && configObject.isJsonObject())
            return new APanelConfig(configObject.getAsJsonObject());
        return null;
    }

}

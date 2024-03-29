package org.example.conf;
import java.io.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

public class Settings {
    private UserSettings userSettings;

    public String fileParser(String filePath) {
        String fileContent = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            bufferedReader.close();

            fileContent = stringBuilder.toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileContent;
    }

    public Settings(String yamlFile) {
        Representer representer = new Representer(new DumperOptions());
        representer.getPropertyUtils().setSkipMissingProperties(true);

        try {
            Yaml yaml = new Yaml(representer);

            String yamlOutput = fileParser(yamlFile);

            this.userSettings = yaml.loadAs(yamlOutput, UserSettings.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public UserSettings getUserSettings() {
        return this.userSettings;
    }
}

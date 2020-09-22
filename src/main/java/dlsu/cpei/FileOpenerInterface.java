package dlsu.cpei;

import javafx.stage.FileChooser;

import java.io.IOException;

interface FileOpenerInterface {
    void openFile();
    void saveFile() throws IOException;
}

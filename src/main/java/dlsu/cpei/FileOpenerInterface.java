package dlsu.cpei;

import java.io.IOException;

interface FileOpenerInterface {
    void openFile();
    void saveFile() throws IOException;
}

package dlsu.cpei;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class OpenFileBridge {
    /*
     * Loads a new notepad session. Answer taken from
     * https://stackoverflow.com/questions/27160951/javafx-open-another-fxml-in-the-another-window-with-button
     * */
    public void loadNotepad(String title, String dir) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notepadProper.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle(title);
            passData(fxmlLoader, dir);
            stage.show();
        } catch(Exception e){
            System.out.println("User exit or some other error");
        }
    }
    public void directoryOpener(Stage stage){
            DirectoryChooser directory = new DirectoryChooser();
            directory.setTitle("Open Notebook");
            try{
                File selectedDirectory = directory.showDialog(stage);
                loadNotepad(selectedDirectory.getName(), selectedDirectory.getPath());
                stage.close();
            } catch(Exception e){
                //TODO file error dialogue
                e.printStackTrace();
            }
        }

    private void passData(FXMLLoader fxmlLoader, String currentDirectory){
        NotepadProperController controller = fxmlLoader.getController();
        controller.initData(currentDirectory);
    }
}

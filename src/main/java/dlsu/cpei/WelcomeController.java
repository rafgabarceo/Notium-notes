/*
* This controls the welcoming screen whenever the application is launched.
* The user will be able to choose to either open a Notebook or create a new Notebook.
* */
package dlsu.cpei;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WelcomeController implements FileOpenerInterface{
    public VBox mainBox;
    private OpenFileBridge fileBridge = new OpenFileBridge();

    /*
    * This method is similar to that of the saveFile function. However,
    * it will only open directories.
    *
    * I'm planning to maybe add the tree-list view in the
    * notebook itself. This is so that the user can simply click on an
    * item instead of pressing open in the notepadProper window.
    *
    * Will try to find a way to implement it properly.
    *
    * I've recently learned about something called the DirectoryChooser. I think that
    * Will be a better thing to use in our case. Instead of opening files, it will open
    * Directories, which will usually act as the notebooks.
    * */
    @FXML
    public void openFile(){ //for open notebook
        Stage stage = (Stage) mainBox.getScene().getWindow();
        fileBridge.directoryOpener(stage);
    }

    @FXML
    public void saveFile(){ //for new notebook
        Stage stage = (Stage) mainBox.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileOpener(file);
        file.setTitle("Create Directory");
        try{
            File selectedFile = file.showSaveDialog(stage);
            Files.createDirectory(Paths.get(selectedFile.getPath()));
            String dir = selectedFile.getPath();
            fileBridge.loadNotepad(selectedFile.getName(), dir);
            stage.close();
        } catch(Exception e){
            //TODO file error dialogue
            e.printStackTrace();
        }
    }
    public void configureFileOpener(FileChooser fileChooser){
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}

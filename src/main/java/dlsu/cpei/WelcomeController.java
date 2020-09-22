/*
* This controls the welcoming screen whenever the application is launched.
* The user will be able to choose to either open a Notebook or create a new Notebook.
* */
package dlsu.cpei;

import dlsu.cpei.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WelcomeController implements FileOpenerInterface{
    public VBox mainBox;
    public TextField newNotebookName;
    private String currentDirectory;

    @FXML
    public void openFile(){ //for open notebook

    }
    @FXML
    public void saveFile(){ //for new notebook
        String newDirectory = newNotebookName.getCharacters().toString();
        Stage stage = (Stage) mainBox.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileOpener(file);
        file.setInitialFileName(newDirectory);
        file.setTitle("Create Directory");
        try{
            File selectedFile = file.showSaveDialog(stage);
            byte[] strToBytes = newNotebookName.getCharacters().toString().getBytes();
            Files.createDirectory(Paths.get(selectedFile.getPath()));
        } catch(Exception e){
            //TODO file error dialogue
            System.out.println("Error!"); //will replace with its own dialogue
        }
    }
    public void configureFileOpener(FileChooser fileChooser){
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WelcomeController implements FileOpenerInterface{
    public VBox mainBox;

    @FXML
    public void openFile(){ //for open notebook
        /*Stage stage = (Stage) mainBox.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileOpener(file);
        file.setTitle("Open Notebook");
        try{
            File selectedFile = file.showOpenDialog(stage);
            Files.createDirectory(Paths.get(selectedFile.getPath()));
            String dir = selectedFile.getPath();
            loadNotepad(selectedFile.getName(), dir);
            stage.close();
        } catch(Exception e){
            //TODO file error dialogue
            e.printStackTrace();
        }*/
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
            loadNotepad(selectedFile.getName(), dir);
            stage.close();
        } catch(Exception e){
            //TODO file error dialogue
            e.printStackTrace();
        }
    }
    /*
    * Loads a new notepad session. Answer taken from
    * https://stackoverflow.com/questions/27160951/javafx-open-another-fxml-in-the-another-window-with-button
    * */
    private void loadNotepad(String title, String dir) throws IOException {
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
    /*
    * Handles passing over of data into another controller.
    * Answer taken from: https://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
    *
    * */
    private void passData(FXMLLoader fxmlLoader, String currentDirectory){
        NotepadProperController controller = fxmlLoader.getController();
        controller.initData(currentDirectory);
    }
    public void configureFileOpener(FileChooser fileChooser){
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}

package dlsu.cpei;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import dlsu.cpei.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
    public TextArea mainArea;
    public AnchorPane anchorPane;

    @FXML
    private void readText() throws IOException{
        System.out.println(mainArea.getText());
    }

    @FXML
    private void openText() throws IOException{
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileChooser(file);
        file.setTitle("Open markdown");
        File selectedFile = file.showOpenDialog(stage);

        if(selectedFile != null){
            String mdContents = Files.readString(Path.of(selectedFile.getPath()));
            mainArea.setText(mdContents);
        } else {
            System.out.println("Error");
        }
    }

    @FXML
    private void saveText() throws IOException{
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileChooser(file);
        file.setInitialFileName("note.md");
        file.setTitle("Save .md");
        try{
            File selectedFile = file.showSaveDialog(stage);
            byte[] strToBytes = mainArea.getText().getBytes(); //converts the mainArea text to bytes
            Files.write(Path.of(selectedFile.getPath()), strToBytes); //writes to the file name given by the user
        } catch(Exception e){
            System.out.println("Error!");
        }
    }

    private static void configureFileChooser(FileChooser fileChooser){
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Markdown", "*.md"));
    }
}

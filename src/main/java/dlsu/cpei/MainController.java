package dlsu.cpei;

import java.io.File;
import java.io.IOException;

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
        file.showOpenDialog(stage);
    }

    @FXML
    private void saveText() throws IOException{
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileChooser(file);
        file.setTitle("Save .md");
        file.showSaveDialog(stage);
    }

    private static void configureFileChooser(FileChooser fileChooser){
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Markdown", "*.md"));
    }
}

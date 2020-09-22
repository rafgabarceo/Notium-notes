/*
* MainController.java that controls the main.fxml file
*
* */package dlsu.cpei;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import dlsu.cpei.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MainController {
    public TextArea mainArea;
    public AnchorPane anchorPane;
    public Text currentFile;
    public Text changeNotifier;
    private String filePath = null;

    @FXML
    private void readText() throws IOException{
        System.out.println(mainArea.getText());
    }

    @FXML
    private void newFile(){

    }
    /*
    *
    * Handles opening a markdown file. The file chooser has a filter applied,
    * meaning that it cannot detect any other files aside from .md.
    * This is to ensure that the user only opens .md files so that the render can
    * easily convert to a working view page.
    *
    * */
    @FXML
    private void openText() throws IOException{
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileChooser(file);
        file.setTitle("Open markdown");
        File selectedFile = file.showOpenDialog(stage);
        try{
            String mdContents = Files.readString(Path.of(selectedFile.getPath()));
            mainArea.setText(mdContents);
            currentFile.setText(selectedFile.getName());
            switchNotify();
        } catch(Exception e){
            //TODO file error dialogue
            System.out.println("Error"); //will replace with its own dialogue
        }
    }


    /*
    * This is a rather simple implementation of saving a file.
    * A new implementation of the saveText was applied. The method will check
    * if the current filePath is empty. If it is, then it means that the user has opened a new file,
    * and will require to open the save dialogue box.
    *
    * If it's not null, it will simply save write to the path given by the filePath String.
    */
    @FXML
    private void saveText() throws IOException{
        if(filePath != null){
            byte[] strToBytes = mainArea.getText().getBytes();
            Files.write(Path.of(filePath), strToBytes);
            switchNotify();
        } else {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            FileChooser file = new FileChooser();
            configureFileChooser(file);
            file.setInitialFileName("note.md");
            file.setTitle("Save .md");
            try{
                File selectedFile = file.showSaveDialog(stage);
                byte[] strToBytes = mainArea.getText().getBytes(); //converts the mainArea text to bytes
                Files.write(Path.of(selectedFile.getPath()), strToBytes); //writes to the file name given by the user
                filePath = selectedFile.getPath();
                currentFile.setText(selectedFile.getName());
                switchNotify();
            } catch(Exception e){
                //TODO file error dialogue
                System.out.println("Error!"); //will replace with its own dialogue
            }
        }

    }

    /*
    * Sets the filters available for the FileChooser. This is what makes it only read
    * .md files
    *
    * */
    private static void configureFileChooser(FileChooser fileChooser){
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Markdown", "*.md"));
    }

    /*
    * Checks if there are changes in the mainArea. If there is, a  * will be appended to the
    * currentFile Text.
    *
    * */
    @FXML
    private void notifyChanges(){
        changeNotifier.setVisible(true);
    }


    /*
    * Switches to the state of notifier.
    * */
    private void switchNotify(){
        if(changeNotifier.isVisible()){
            changeNotifier.setVisible(false);
        } else {
            changeNotifier.setVisible(true);
        }
    }

    private void convertMDToHTML(){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(mainArea.getText());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        renderer.render(document);
    }

}

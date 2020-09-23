/*
* MainController.java that controls the notepadProper.fxml file
*
* */package dlsu.cpei;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NotepadProperController implements FileOpenerInterface {
    public TextArea mainArea;
    public AnchorPane anchorPane;
    public Text currentFile;
    public Text changeNotifier;
    public WebView webViewPane;
    public Button toggleTreeView;
    public TreeView treeviewPane;
    private String filePath = null;
    private String currentDirectory;
    /*
    * Responsible for setting the current controller's
    * data.
    *
    * */
    void initData(String currentDirectory){
        setCurrentDirectory(currentDirectory);
    }
    private void setCurrentDirectory(String currentDirectory){
        this.currentDirectory = currentDirectory;
    }
    @FXML
    private void readText() throws IOException{
        System.out.println(mainArea.getText());
    }

    @FXML
    private void newFile(){
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        SaveFile saveFile = new SaveFile(stage, currentFile, mainArea, currentDirectory);
        saveFile.saveNote();
        /*
        * Reinitialize the notepad area.
        * */
        mainArea.clear();
        changeNotifier.setVisible(false);
        filePath = null;
        webViewPane.getEngine().loadContent("");
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
    public void openFile(){
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser file = new FileChooser();
        configureFileOpener(file);
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
    public void saveFile() throws IOException{
        if(filePath != null){
            byte[] strToBytes = mainArea.getText().getBytes();
            Files.write(Path.of(filePath), strToBytes);
            switchNotify();
        } else {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            SaveFile saveFile = new SaveFile(stage, currentFile, mainArea, currentDirectory);
            saveFile.saveNote();
        }
    }

    /*
    * Sets the filters available for the FileChooser. This is what makes it only read
    * .md files
    *
    * */
    public void configureFileOpener(FileChooser fileChooser){
        fileChooser.setInitialDirectory(new File(currentDirectory));
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
        changeNotifier.setVisible(!changeNotifier.isVisible());
    }

    @FXML
    private void invokeTree(){
        NotebookView notebookView = new NotebookView(currentDirectory, treeviewPane);
    }
    /*
    * Converts the markdown into HTML that the WebViewPane
    * will be able to showcase.
    * */
    @FXML
    private void showcaseHTML(){
        Renderer renderer = new Renderer();
        String renderedDocument = renderer.renderDocument(renderer.convertStringToNode(mainArea.getText()));
        webViewPane.getEngine().loadContent(renderedDocument, "text/html");
    }

    /*Handles events that happen when the user clicks on a notebook in the TreeView*/
    public void mouseClick(MouseEvent mouseEvent) throws IOException {
        TreeItem item = (TreeItem) treeviewPane.getSelectionModel().getSelectedItem();
    }
}

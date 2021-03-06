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
    private OpenFileBridge fileBridge = new OpenFileBridge();
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
        fileBridge.directoryOpener(stage);
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

    @FXML
    public void saveFileClassWrapper() throws IOException {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        SaveFile saveFile = new SaveFile(stage, currentFile, mainArea, currentDirectory);
        saveFile.saveNoteNoDialogue(new File(currentDirectory + "/" +currentFile.getText()));
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
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        SaveFile filePlayer = new SaveFile(stage, currentFile, mainArea, currentDirectory);
        try{
            File filePrevious = new File(currentDirectory + "/" +currentFile.getText());
            File fileNext = new File(currentDirectory + "/" + item.getValue());
            filePlayer.saveNoteNoDialogue(filePrevious);
            String refresh = filePlayer.readNoteForOpen(fileNext);
            mainArea.setText(refresh);
            currentFile.setText(fileNext.getName());
        } catch(Exception e){
            File fileNext = new File(currentDirectory + "/" + item.getValue());
            String refresh = filePlayer.readNoteForOpen(fileNext);
            mainArea.setText(refresh);
            currentFile.setText(fileNext.getName());
        }

    }
}

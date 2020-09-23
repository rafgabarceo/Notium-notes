/*
* This class is terribly named by mistake. Refactoring the entire class
* results to errors that I'm too busy to delve into.
*
* This class handles saving, opening files that are not dependent on the controller.
* This class is not used in the welcome screen yet but will be implemented when feasible.
* */
package dlsu.cpei;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveFile {
    private String filePath;
    private Stage stage;
    private Text currentTitle;
    private TextArea noteContent;
    private String savingDirectory;
    public SaveFile(Stage stage, Text currentTitle, TextArea noteContent, String savingDirectory){
        this.stage = stage;
        this.currentTitle = currentTitle;
        this.noteContent = noteContent;
        this.savingDirectory = savingDirectory;
    }
    public void saveNote(){
        FileChooser file = new FileChooser();
        file.setInitialDirectory(new File(savingDirectory));
        file.setInitialFileName("note.md");
        file.setTitle("Save .md");
        try{
            File selectedFile = file.showSaveDialog(stage);
            byte[] strToBytes = noteContent.getText().getBytes(); //converts the mainArea text to bytes
            Files.write(Path.of(selectedFile.getPath()), strToBytes); //writes to the file name given by the user
            filePath = selectedFile.getPath();
            currentTitle.setText(selectedFile.getName());
        } catch(Exception e){
            //TODO file error dialogue
            System.out.println("Error!"); //will replace with its own dialogue
        }
    }
    /*
    * Does not include the Dialogue when saving. Rather, the user will have to pass it into the function.
    * */
    public void saveNoteNoDialogue(File selectedFile) throws IOException {
        byte[] strToBytes = noteContent.getText().getBytes(); //converts the mainArea text to bytes
        Files.write(Path.of(selectedFile.getPath()), strToBytes); //writes to the file name given by the user
    }
    /*
    * Opens a new note without a dialogue.
    * */
    public String readNoteForOpen(File selectedFile) throws IOException {
        String mdContents = Files.readString(Path.of(selectedFile.getPath()));
        return mdContents;
    }

}

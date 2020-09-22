package dlsu.cpei;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
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

}

/*
* This class handles the creation and scanning of the files
* that are currently present in a directory
* */
package dlsu.cpei;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

public class NotebookView {
    private ArrayList<TreeItem> notes;
    private String currentDirectory;
    private TreeView treeView;
    public NotebookView(String currentDirectory, TreeView treeView){
        this.currentDirectory = currentDirectory;
        this.notes = scanDirectory();
        this.treeView = treeView;
        TreeItem rootFolder = new TreeItem(currentDirectory);
        buildTree(rootFolder);

    }
    /*
    * Builds the tree that will be showcased when the user presses show notes
    *
    * */
    public void buildTree(TreeItem rootFolder){
        treeView.setRoot(rootFolder);
        rootFolder.setExpanded(true);
        for(TreeItem item : notes){
            rootFolder.getChildren().add(item);
        }
    }
    /*
    * Responsible for showing the current files in the directory chosen by the user.
    *
    * */
    private ArrayList<TreeItem> scanDirectory(){
        ArrayList<TreeItem> notes = new ArrayList<TreeItem>();
        String[] pathNames;
        File directory = new File(currentDirectory);
        pathNames = directory.list();
        for(String note : pathNames){
            TreeItem noteNode = new TreeItem(note);
            notes.add(noteNode);
        }
        return notes;
    }
}

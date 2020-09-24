/*
* This class handles the creation and scanning of the files
* that are currently present in a directory
* */
package dlsu.cpei;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;

public class NotebookView {
    private ArrayList<TreeItem<String>> notes;
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
    private ArrayList<TreeItem<String>> scanDirectory(){
        ArrayList<TreeItem<String>> notes = new ArrayList<TreeItem<String>>();
        String[] pathNames;
        try{
            File directory = new File(currentDirectory);
            pathNames = directory.list();
            for(String note : pathNames){
                TreeItem<String> noteNode = new TreeItem(note);
                notes.add(noteNode);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return notes;
    }
}

import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JButton;
public class FolderChooser extends JButton{
    private BagOfWords bagOfWords;
    private String category;
    private JFileChooser folderChooser;
    public FolderChooser(String category){
        this.category = category;
        this.setText("Select " + this.category + " Folder");
        // Initialize folderChooser
        this.folderChooser = new JFileChooser();
        this.folderChooser.setCurrentDirectory(new File("."));
        this.folderChooser.setDialogTitle("Open " + this.category + " Folder");
        this.folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.folderChooser.setAcceptAllFileFilterUsed(false);
    }

    public File getAbsolutePath(){
        return this.folderChooser.getSelectedFile();
    }
    public File[] openFileChooser(){
        if(this.folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            return this.folderChooser.getSelectedFile().listFiles();
        }
        return null;
    }


}


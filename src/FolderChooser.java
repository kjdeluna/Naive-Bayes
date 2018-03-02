import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JButton;
public class FolderChooser extends JButton implements ActionListener{
    private BagOfWords bagOfWords;
    private JTable table;
    private JLabel label;
    private SolutionPanel sp;
    public FolderChooser(String text, BagOfWords bagOfWords, JTable table, JLabel label, SolutionPanel sp){
        this.setText(text);
        this.bagOfWords = bagOfWords;
        this.table = table;
        this.label = label;
        this.addActionListener(this);
        this.sp = sp;
    }
    // @Override
    public void actionPerformed(ActionEvent e){
        JFileChooser chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Open Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File[] directoryListing = chooser.getSelectedFile().listFiles();
            if(directoryListing != null){
                bagOfWords.resetAll();
                for(File child : directoryListing){
                    // Get absolute path and loop its contents
                    this.bagOfWords.readFile(chooser.getSelectedFile() + "/"+ child.getName());
                    System.out.println(child);
                    }
                }
            }
            convertToTable();
            sp.updateDictSize();
        }

	private void convertToTable(){
		DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
		tableModel.setRowCount(0);
        // System.out.println(tableModel);
        // return;
		label.setText("Total Words: " + Integer.toString(this.bagOfWords.getWordCount()));
		for(String key : this.bagOfWords.getDict().keySet()){
			String[] data = new String[2];
			data[0] = key;
			data[1] = Integer.toString(this.bagOfWords.getDict().get(key));
			tableModel.addRow(data);
		}
		this.table.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}
}


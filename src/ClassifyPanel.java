// import java.awt.BorderLayout;
// import java.awt.Dimension;
// import java.awt.event.ActionListener;
// import java.awt.event.ActionEvent;
// import javax.swing.JFileChooser;
// import javax.swing.JButton;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.JViewport;
// import javax.swing.table.DefaultTableModel;
// import java.io.File;

// public class ClassifyPanel extends JPanel{
//     private JLabel dictionarySizeLabel;
//     private JLabel totalWordsLabel;
//     private FolderChooser folderChooserButton;
//     private JTable table;
//     public ClassifyPanel(){
//         // Setting Panel properties
//         this.setLayout();
//         this.setPreferredSize(new Dimension(Main.WIDTH/3), Main.HEIGHT);

//         // Initializing components
//             // Label
//         this.dictionarySizeLabel = new JLabel("Dictionary Size: 0");
//         this.totalWordsLabel = new JLabel("Total Words: 0");

//             // FolderChooser
//         this.folderChooserButton = new FolderChooser(this.category);
//         this.folderChooserButton.addActionListener(new ActionListener(){
//             @Override
//             public void actionPerformed(ActionEvent e){
//                 File[] directoryListing = folderChooserButton.openFileChooser();
//                 if(directoryListing != null){
//                     for(File child : directoryListing){
//                         // Get absolute path and loop its contents
//                         // bagOfWords.readFile(folderChooserButton.getAbsolutePath()+ "/" + child.getName());
//                         System.out.println(folderChooserButton.getAbsolutePath()+ "/" + child.getName());
//                     }
//                     convertToTable();
//                 }
//             }
//         });
//             // Table
//         String[] columnNames = {
//             "Filename",
//             "Class",
//             "P(Spam)"
//         };
//     }
// 	private void convertToTable(){  
// 		// DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
// 		// tableModel.setRowCount(0);
//         // // Update Total Words
// 		// this.totalWordsLabel.setText("Total Words in " + this.category + ": " + Integer.toString(this.bagOfWords.getWordCount()));
//         // // Iterate over the hashmap, bag of words
// 		// for(String key : this.bagOfWords.getDict().keySet()){
//         //     // Get data >>> key : frequency
// 		// 	String[] data = new String[2];
// 		// 	data[0] = key;
// 		// 	data[1] = Integer.toString(this.bagOfWords.getDict().get(key));
//         //     // Add a row in table model
// 		// 	tableModel.addRow(data);
// 		// }
//         // // Update the model of the table
// 		// this.table.setModel(tableModel);
//         // // Force it to update
// 		// tableModel.fireTableDataChanged();
// 	}
// }
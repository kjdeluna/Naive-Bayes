import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Graphics;
import javax.swing.JViewport;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
public class SolutionPanel extends JPanel{
	private JButton showDirectoryChooserButton;
	private JFileChooser chooser;
	private BagOfWords bagOfWords;
	private String chooserTitle = "Open Directory";
	private JScrollPane tableScrollPane;
	private JTable table;
	private JLabel totalWordsLabel;
	private JLabel dictSize;
	public SolutionPanel(BagOfWords bagOfWords){
		// Panel
		this.bagOfWords = bagOfWords;
		this.setLayout(new BorderLayout());
		// this.totalWords = new JLabel();
		// this.dictSize = new JLabel();
		// this.add(totalWords);
		// this.add(dictSize);
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new BorderLayout());
		// Show Directory Button
		this.showDirectoryChooserButton = new JButton("Open Directory");
		this.showDirectoryChooserButton.setFocusable(false);
		String[] columnNames = {
			"Word",
			"Frequency" 
		};
		// this.table = new JTable(tableData,columnNames);
		// Repopulate data
		// Add to Panel
		Object[][] data = new Object[0][0];
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		this.totalWordsLabel = new JLabel("Total Words: 0");
		leftPanel.add(totalWordsLabel, BorderLayout.SOUTH);
		leftPanel.add(this.showDirectoryChooserButton, BorderLayout.NORTH);
		// leftPanel.add(this.table, BorderLayout.CENTER);
		// Button Action Listener
		Object[][] rowData = new Object[0][0];
		this.table = new JTable(model);
		this.tableScrollPane = new JScrollPane(this.table);
		this.tableScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		leftPanel.add(tableScrollPane, BorderLayout.WEST);
		this.add(leftPanel);	
		
		this.showDirectoryChooserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			    chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new File("."));
			    chooser.setDialogTitle(chooserTitle);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			    	File[] directoryListing = chooser.getSelectedFile().listFiles();
			    	if(directoryListing != null){
			    		bagOfWords.resetAll();
			    		for(File child : directoryListing){
			    			// Get absolute path and loop its contents
			    			bagOfWords.readFile(chooser.getSelectedFile() + "/"+ child.getName());
			    			System.out.println(child);
			    		}
			    	}
			    }
			    convertToTable();
	     }
		});
	}
	private void convertToTable(){
		DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
		tableModel.setRowCount(0);
 
		totalWordsLabel.setText("Total Words: " + Integer.toString(this.bagOfWords.getWordCount()));
		// dictSize = new JLabel("Dict Size: " + Integer.toString(this.bagOfWords.getDict().size()));
		// dictSize.setBounds(0, 30, 300, 30);
		// this.add(totalWords);
		// this.add(dictSize);
		// Repopulate data
		// Put the data to table
		for(String key : this.bagOfWords.getDict().keySet()){
			String[] data = new String[2];
			data[0] = key;
			System.out.println(data[0]);
			data[1] = Integer.toString(this.bagOfWords.getDict().get(key));
			tableModel.addRow(data);
		}
		this.table.setModel(tableModel);
		tableModel.fireTableDataChanged();
			// Ask JPanel to repaint since data has changed
		this.repaint();

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
}
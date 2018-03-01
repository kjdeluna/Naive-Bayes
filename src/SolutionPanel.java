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

public class SolutionPanel extends JPanel{
	private JButton showDirectoryChooserButton;
	private JFileChooser chooser;
	private BagOfWords bagOfWords;
	private String chooserTitle = "Open Directory";
	private JScrollPane tableScrollPane;
	private JTable table;
	private JLabel totalWords;
	private JLabel dictSize;
	public SolutionPanel(BagOfWords bagOfWords){
		// Panel
		this.bagOfWords = bagOfWords;
		this.totalWords = new JLabel();
		this.dictSize = new JLabel();
		this.add(totalWords);
		this.add(dictSize);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
		this.setBounds(0,0,Main.WIDTH, Main.HEIGHT);
		// Show Directory Button
		this.showDirectoryChooserButton = new JButton("Open Directory");
		this.showDirectoryChooserButton.setFocusable(false);
		this.showDirectoryChooserButton.setBounds(Main.WIDTH - 150, Main.HEIGHT-30, 150, 30);
		// Add to Panel
		this.add(this.showDirectoryChooserButton);
		// Button Action Listener
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
		// Remove JLabels
		this.remove(totalWords);
		this.remove(dictSize);
		totalWords = new JLabel("Total Words: " + Integer.toString(this.bagOfWords.getWordCount()));
		totalWords.setBounds(0,0,300,30);
		dictSize = new JLabel("Dict Size: " + Integer.toString(this.bagOfWords.getDict().size()));
		dictSize.setBounds(0, 30, 300, 30);
		this.add(totalWords);
		this.add(dictSize);
		String[] columnNames = {
			"Word",
			"Frequency" 
		};
		// Repopulate data
		Object[][] tableData = new Object[this.bagOfWords.getDict().size()][2];
		int index = 0;
		// Put the data to table
		for(String key : this.bagOfWords.getDict().keySet()){
			tableData[index][0] = key;
			tableData[index][1] = this.bagOfWords.getDict().get(key);
			index++;
			this.table = new JTable(tableData,columnNames);
			this.tableScrollPane = new JScrollPane(this.table);
			this.tableScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
			this.tableScrollPane.setBounds(50, 100, 200, 200);
			this.add(tableScrollPane);
			// Ask JPanel to repaint since data has changed
			this.repaint();

		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
}
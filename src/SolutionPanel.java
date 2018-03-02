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
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;
public class SolutionPanel extends JPanel{
	private FolderChooser hamShowDirectoryChooserButton;
	private FolderChooser spamShowDirectoryChooserButton;
	private JFileChooser chooser;
	private BagOfWords ham;
	private BagOfWords spam;
	private JScrollPane hamTableScrollPane;
	private JScrollPane spamTableScrollPane;
	private JTable hamTable;
	private JTable spamTable;
	private JLabel hamTotalWordsLabel;
	private JLabel spamTotalWordsLabel;
	private JLabel dictSize;
	private JPanel statsWrapper;
	private int count = 0;
	public SolutionPanel(BagOfWords ham, BagOfWords spam){
		// Panel
		this.ham = ham;
		this.spam = spam;
		this.setLayout(new BorderLayout());
		// this.totalWords = new JLabel();
		// this.dictSize = new JLabel();
		// this.add(totalWords);
		// this.add(dictSize);
		JPanel wrapper = new JPanel(new BorderLayout());
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new BorderLayout());
		JPanel statsWrapper = new JPanel(new GridLayout(1,2));
		this.hamTotalWordsLabel = new JLabel("Total Words: 0");
		this.spamTotalWordsLabel = new JLabel("Total Words: 0");
		// Show Directory Button
		leftPanel.setPreferredSize(new Dimension(Main.WIDTH/2, Main.HEIGHT));
		rightPanel.setPreferredSize(new Dimension(Main.WIDTH/2, Main.HEIGHT));
		// hamShowDirectoryChooserButton.addActionListener();	
		// spamShowDirectoryChooserButton.addActionListener();
		String[] columnNames = {
			"Word",
			"Frequency" 
		};
		// Repopulate data
		// Add to Panel
		Object[][] hamRowData = new Object[0][0];
		Object[][] spamRowData = new Object[0][0];
		
		DefaultTableModel hamModel = new DefaultTableModel(hamRowData, columnNames);
		DefaultTableModel spamModel = new DefaultTableModel(spamRowData, columnNames);
		// leftPanel.add(this.table, BorderLayout.CENTER);
		// Button Action Listener
		// Object[][] rowData = new Object[0][0];
		this.hamTable = new JTable(hamModel);
		this.spamTable = new JTable(spamModel);
		
		this.dictSize = new JLabel("Dictionary size: 0");
		this.hamTableScrollPane = new JScrollPane(this.hamTable);
		this.spamTableScrollPane = new JScrollPane(this.spamTable);
		this.hamTableScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		this.hamShowDirectoryChooserButton = new FolderChooser("Open Directory", this.ham, this.hamTable, this.hamTotalWordsLabel, this);
		this.hamShowDirectoryChooserButton.setFocusable(false);
		this.spamShowDirectoryChooserButton = new FolderChooser("Open Directory", this.spam, this.spamTable, this.spamTotalWordsLabel, this);
		this.spamShowDirectoryChooserButton.setFocusable(false);
		leftPanel.add(this.hamShowDirectoryChooserButton, BorderLayout.NORTH);
		rightPanel.add(this.spamShowDirectoryChooserButton, BorderLayout.NORTH);
		leftPanel.add(hamTableScrollPane, BorderLayout.CENTER);
		rightPanel.add(spamTableScrollPane, BorderLayout.CENTER);
		leftPanel.add(this.hamTotalWordsLabel, BorderLayout.SOUTH);
		statsWrapper.add(this.spamTotalWordsLabel);
		statsWrapper.add(this.dictSize);
		rightPanel.add(statsWrapper, BorderLayout.SOUTH);
		wrapper.add(leftPanel, BorderLayout.WEST);
		wrapper.add(rightPanel, BorderLayout.EAST);
		this.add(wrapper);	
	}
	public void updateDictSize(){
		this.dictSize.setText("Dictionary size: " + Integer.toString(this.ham.getDictSize() + this.spam.getDictSize()));
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.dictSize.setText("Dictionary Size: " + Integer.toString(ham.getDictSize() + spam.getDictSize()));
	}
}
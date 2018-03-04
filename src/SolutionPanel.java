import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class SolutionPanel extends JPanel{
	public static int totalWordsCount = 0;
	public static int dictionarySize = 0;
	public static BagOfWords ham;
	public static BagOfWords spam;
	private BagOfWordsPanel hamPanel;
	private BagOfWordsPanel spamPanel;
	private static JLabel dictionarySizeLabel;
	private static JLabel totalWordsLabel;
	private FolderChooser classifyFolderChooser;
	private JButton filterButton;
	private JTable outputTable;
	private JScrollPane outputTableScrollPane;
	public SolutionPanel(BagOfWords ham, BagOfWords spam){
		// Initializing properties
		this.ham = ham;
		this.spam = spam;
		// Setting the panel
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

		// Initializing ham and spam panels
		this.hamPanel = new BagOfWordsPanel("Ham", ham);
		this.spamPanel = new BagOfWordsPanel("Spam", spam);
			// Labels
        this.dictionarySizeLabel = new JLabel("Dictionary Size: 0");
        this.totalWordsLabel = new JLabel("Total Words: 0");
			// Folder Chooser
		this.classifyFolderChooser = new FolderChooser("Classify");
			// Button
		this.filterButton = new JButton("Filter");
		this.filterButton.setFocusable(false);
			// Table
        String[] outputTableColumnNames = {
            "Filename",
            "Class",
            "P(Spam)"
        };
        Object[][] rowData = new Object[0][0];
        DefaultTableModel model = new DefaultTableModel(rowData, outputTableColumnNames);
        this.outputTable = new JTable(model);
			// ScrollPane
        this.outputTableScrollPane = new JScrollPane(this.outputTable);
        this.outputTableScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		// Adding components to panel
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(Main.WIDTH/3, 50));
			// Dictionary Size and Total number of words
			JPanel labelWrapper = new JPanel(new GridLayout(1, 2));
			labelWrapper.add(dictionarySizeLabel);
			labelWrapper.add(totalWordsLabel);
			// Folder Chooser and Filter Button
			JPanel buttonWrapper = new JPanel(new GridLayout(1, 2));
			buttonWrapper.add(classifyFolderChooser);
			buttonWrapper.add(filterButton);
			// Add Folder Chooser Button Action Listener
		rightPanel.add(buttonWrapper, BorderLayout.NORTH);
		rightPanel.add(outputTableScrollPane, BorderLayout.CENTER);
		rightPanel.add(labelWrapper, BorderLayout.SOUTH);
		this.add(hamPanel, BorderLayout.WEST);
		this.add(spamPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
		this.filterButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ProbabilitySolver ps = new ProbabilitySolver();
			}
		});
	}

	public static void updateTotalWords(){
		totalWordsCount = ham.getWordCount() + spam.getWordCount();
		totalWordsLabel.setText("Total Words: " + Integer.toString(totalWordsCount));
	}
	public static void updateDictionarySize(){
		HashMap<String, Integer> tempHam = ham.getDict();
		HashMap<String, Integer> tempSpam = spam.getDict();
		// Initialize the count to the size of ham
		int count = tempHam.keySet().size();
		// Now, we do not care for the ham keys
		// Check the unique keys in spam
		for(String key : tempSpam.keySet()){
			if(!tempHam.containsKey(key)) count++;
		}
		dictionarySize = count;
		dictionarySizeLabel.setText("Dictionary Size: " + Integer.toString(dictionarySize));
			// The following code is not correct for the reason that it does not consider intersected keys
		// dictionarySize = ham.getDict().keySet().size() + spam.getDict().keySet().size();
		// dictionarySizeLabel.setText("Dictionary Size: " + Integer.toString(dictionarySize));
	}
}
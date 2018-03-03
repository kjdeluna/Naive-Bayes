import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

public class SolutionPanel extends JPanel{
	private BagOfWordsPanel hamPanel;
	private BagOfWordsPanel spamPanel;
	private JLabel dictionarySizeLabel;
	private JLabel totalWordsLabel;
	private FolderChooser classifyFolderChooser;
	private JButton filterButton;
	private JTable outputTable;
	private JScrollPane outputTableScrollPane;
	public SolutionPanel(BagOfWords ham, BagOfWords spam){
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
			// 
		rightPanel.add(buttonWrapper, BorderLayout.NORTH);
		rightPanel.add(outputTableScrollPane, BorderLayout.CENTER);
		rightPanel.add(labelWrapper, BorderLayout.SOUTH);
		this.add(hamPanel, BorderLayout.WEST);
		this.add(spamPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
	}
}
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class SolutionPanel extends JPanel{
	private BagOfWordsPanel hamPanel;
	private BagOfWordsPanel spamPanel;
	public SolutionPanel(BagOfWords ham, BagOfWords spam){
		// Setting the panel
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

		// Initializing ham and spam panels
		this.hamPanel = new BagOfWordsPanel("Ham", ham);
		this.spamPanel = new BagOfWordsPanel("Spam", spam);

		// Adding components to panel
		this.add(hamPanel, BorderLayout.WEST);
		this.add(spamPanel, BorderLayout.CENTER);
	}
}
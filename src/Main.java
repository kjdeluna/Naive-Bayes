import javax.swing.JFrame;
import java.awt.Dimension;
public class Main{
	public static final int WIDTH = 640;
	public static final int HEIGHT = 320;
	public static void main(String[] args){
		// Create frame
		JFrame frame = new JFrame("Bag Of Words");
		BagOfWords bagOfWordsHashMap = new BagOfWords();
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setLayout(null);
		SolutionPanel sp = new SolutionPanel(bagOfWordsHashMap);
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
    }
}
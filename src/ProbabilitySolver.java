import java.math.BigDecimal;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.math.RoundingMode;
public class ProbabilitySolver{
    private BigDecimal probSpam;
    private BigDecimal probHam;
    private String filename = "019";
    public ProbabilitySolver(){
        this.probSpam = getProbSpam();
        this.probHam = getProbHam();
        // this.computeProbabilitySpamGiven();
        // System.out.println(computeProbabilitySpamGivenMessage().toString());
        // System.out.println(computeProbabilityMessageGivenSpam());
        System.out.println(getProbSpam().toString());
        System.out.println(getProbHam().toString());
        System.out.println(computeProbabilityMessageGivenSpam().toString());
        System.out.println(computeProbabilityMessageGivenHam().toString());
        System.out.println(computeProbabilitySpamGivenMessage().toString());
    }

    private BigDecimal getProbSpam(){
        return new BigDecimal(Double.toString((double) SolutionPanel.spam.getWordCount() / 
            ((double) SolutionPanel.spam.getWordCount() + (double) SolutionPanel.ham.getWordCount()))
        );
    }
    private BigDecimal getProbHam(){
        return new BigDecimal(Double.toString((double) SolutionPanel.ham.getWordCount() / 
            ((double) SolutionPanel.spam.getWordCount() + (double) SolutionPanel.ham.getWordCount()))
        );
    }
    private BigDecimal getProbWordGivenSpam(String word){
        if(SolutionPanel.spam.getDict().get(word) == null){
            // System.out.println("Word: " + word);
            return new BigDecimal("1.0");
        }
        return new BigDecimal(Double.toString((double) SolutionPanel.spam.getDict().get(word) / (double) SolutionPanel.spam.getWordCount()));
    }
    private BigDecimal computeProbWordGivenHam(String word){
        if(SolutionPanel.ham.getDict().get(word) == null){
            return new BigDecimal("1.0");
        }
        return new BigDecimal(Double.toString((double) SolutionPanel.ham.getDict().get(word) / (double) SolutionPanel.ham.getWordCount()));
    }
	private BigDecimal computeProbabilityMessageGivenSpam(){
		String[] tokens;
		String word;
        BigDecimal accumulator = new BigDecimal("1.0");
		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			while((currentLine = br.readLine()) != null){
                // System.out.println(currentLine);
				tokens = currentLine.split("\\s");
				for(String token:tokens){
                    // System.out.println(Arrays.toString(tokens));
					// Lowercase all . Replace all non-alphanumeric characters
					word = token.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    // System.out.println("Probability: "+ getProbWordGivenSpam(word));
                    accumulator = accumulator.multiply(getProbWordGivenSpam(word));
                    // System.out.println("accu" + accumulator.toString());
                    // System.out.println(SolutionPanel.spam.getDict().get(word));
				}
			}
		} catch (IOException e){
			// e.printStackTrace();
		}
        return accumulator;
	}
    private BigDecimal computeProbabilityMessageGivenHam(){
		String[] tokens;
		String word;
        BigDecimal accumulator = new BigDecimal("1.0");
		try{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			while((currentLine = br.readLine()) != null){
                // System.out.println(currentLine);
				tokens = currentLine.split("\\s");
				for(String token:tokens){
					// Lowercase all . Replace all non-alphanumeric characters
					word = token.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    accumulator = accumulator.multiply(computeProbWordGivenHam(word));
                    // System.out.println("accu" + accumulator.toString());
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
        return accumulator;
    }
    private BigDecimal computeProbabilitySpamGivenMessage(){
        return 
            (computeProbabilityMessageGivenSpam().multiply(getProbSpam()))
            .divide((computeProbabilityMessageGivenSpam().multiply(getProbSpam())
                    .add(computeProbabilityMessageGivenHam().multiply(getProbHam()))), 10, RoundingMode.HALF_EVEN);
            
            // ((computeProbabilityMessageGivenSpam().multiply(getProbSpam()).add((computeProbabilityMessageGivenHam().multiply(getProbHam())))));
    }
}
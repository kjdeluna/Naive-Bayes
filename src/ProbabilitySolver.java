import java.math.BigDecimal;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
public class ProbabilitySolver{
    private String path;
    private String filename;
    private int k; // Smoothing factor, k
    private int newWordsCount;
    private static final double THRESHOLD = 0.50;

    public void setFolder(String path, String filename){
        this.path = path;
        this.filename = filename;
    }
    public void setK(int k){
        this.k = k;
    }
    public String[] getOutput(){
        String[] output = new String[3];
        String classify;
        BigDecimal computedProbability = computeProbabilitySpamGivenMessage();
        if(computedProbability == null){
            // If it encountered Arithmetic Exception
            output[2] = "Cannot be processed";
            output[1] = "undefined";
        } else{
            classify = computedProbability.compareTo(new BigDecimal(Double.toString(THRESHOLD))) >= 0 ? "SPAM" : "HAM"; 
            output[2] = computedProbability.toString();
            output[1] = classify;
        }
        output[0] = filename;
        return output;
    }
    private BigDecimal getProbSpam(){
        return new BigDecimal(Double.toString(((double) SolutionPanel.spam.getFileCount())  / 
            ((double) SolutionPanel.spam.getFileCount() + (double) SolutionPanel.ham.getFileCount()))
        );
    }
    private BigDecimal getProbHam(BigDecimal P_Spam){
        return BigDecimal.ONE.subtract(P_Spam);
    }
    private BigDecimal computeProbWordGivenSpam(String word){
        BigDecimal spamWord;
        BigDecimal spamWordCount;
        spamWord = new BigDecimal(Integer.toString(SolutionPanel.spam.getDict().get(word)));
        spamWordCount = new BigDecimal(Integer.toString(SolutionPanel.spam.getWordCount()));
        return spamWord.divide(spamWordCount, 10, RoundingMode.HALF_EVEN);
    }
    private BigDecimal computeProbWordGivenHam(String word){
        BigDecimal hamWord;
        BigDecimal hamWordCount;
        hamWord = new BigDecimal(Integer.toString(SolutionPanel.ham.getDict().get(word)));
        hamWordCount = new BigDecimal(Integer.toString(SolutionPanel.ham.getWordCount()));
        return hamWord.divide(hamWordCount, 10, RoundingMode.HALF_EVEN);
    }
	private BigDecimal computeProbabilityMessageGivenSpam(){
		String[] tokens;
		String word;
        BigDecimal accumulator = new BigDecimal("1.0");
		try{
			FileReader fr = new FileReader(this.path + "/" + this.filename);
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			while((currentLine = br.readLine()) != null){
				tokens = currentLine.split("\\s");
				for(String token:tokens){
					// Lowercase all . Replace all non-alphanumeric characters
					word = token.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    if(word.trim().length() > 0){
                        accumulator = accumulator.multiply(computeProbWordGivenSpam(word));
                    }
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
			FileReader fr = new FileReader(this.path + "/" + this.filename);
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			while((currentLine = br.readLine()) != null){
				tokens = currentLine.split("\\s");
				for(String token:tokens){
					// Lowercase all . Replace all non-alphanumeric characters
					word = token.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    if(word.trim().length() > 0){
                        accumulator = accumulator.multiply(computeProbWordGivenHam(word));
			    	}
                }
			}
		} catch (IOException e){
			e.printStackTrace();
		}
        return accumulator;
    }
    private BigDecimal computeProbabilitySpamGivenMessage(){
        BigDecimal P_Spam = getProbSpam();
        BigDecimal P_Ham = getProbHam(P_Spam);
        BigDecimal numerator = computeProbabilityMessageGivenSpam().multiply(P_Spam);
        BigDecimal P_Message = computeProbabilityMessageGivenSpam().multiply(P_Spam)
                                .add(computeProbabilityMessageGivenHam().multiply(P_Ham));
        try{
            return numerator.divide(P_Message, 10, RoundingMode.HALF_EVEN);
        } catch(ArithmeticException e){
            return null;
        }       
    }
}

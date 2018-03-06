import java.math.BigDecimal;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.math.RoundingMode;
public class ProbabilitySolver{
    private String path;
    private String filename;
    public ProbabilitySolver(){}
    public void setFolder(String path, String filename){
        this.path = path;
        this.filename = filename;
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
            classify = computedProbability.compareTo(new BigDecimal("0.50")) >= 0 ? "SPAM" : "HAM"; 
            output[2] = computedProbability.toString();
            output[1] = classify;
        }
        output[0] = filename;
        return output;
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
        if(SolutionPanel.spam.getDict().containsKey(word) && !SolutionPanel.ham.getDict().containsKey(word)){
            return new BigDecimal("1.0");
        }
        else if(!SolutionPanel.spam.getDict().containsKey(word)){
            return new BigDecimal("0.0");
        }
        BigDecimal spamWord = new BigDecimal(Integer.toString(SolutionPanel.spam.getDict().get(word)));
        BigDecimal spamWordCount = new BigDecimal(Integer.toString(SolutionPanel.spam.getWordCount()));
        return spamWord.divide(spamWordCount, 300, RoundingMode.HALF_EVEN);
    }
    private BigDecimal computeProbWordGivenHam(String word){
        if(SolutionPanel.ham.getDict().containsKey(word) && !SolutionPanel.spam.getDict().containsKey(word)){
            return new BigDecimal("1.0");
        }
        else if(!SolutionPanel.ham.getDict().containsKey(word)){
            return new BigDecimal("0.0");
        }
        BigDecimal spamWord = new BigDecimal(Integer.toString(SolutionPanel.ham.getDict().get(word)));
        BigDecimal spamWordCount = new BigDecimal(Integer.toString(SolutionPanel.ham.getWordCount()));
        return spamWord.divide(spamWordCount, 300, RoundingMode.HALF_EVEN);
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
                        accumulator = accumulator.multiply(getProbWordGivenSpam(word));
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
        BigDecimal numerator = computeProbabilityMessageGivenSpam().multiply(getProbSpam());
        BigDecimal denominator = computeProbabilityMessageGivenSpam().multiply(getProbSpam())
                                .add(computeProbabilityMessageGivenHam().multiply(getProbHam()));
        try{
            return numerator.divide(denominator, 10, RoundingMode.HALF_EVEN);
        } catch(ArithmeticException e){
            return null;
        }          
    }
}

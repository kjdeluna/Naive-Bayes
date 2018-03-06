import java.math.BigDecimal;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.math.RoundingMode;
public class ProbabilitySolver{
    private BigDecimal probSpam;
    private BigDecimal probHam;
    private String filename = "021";
    public ProbabilitySolver(){
        this.probSpam = getProbSpam();
        this.probHam = getProbHam();
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
        if(SolutionPanel.spam.getDict().containsKey(word) && !SolutionPanel.ham.getDict().containsKey(word)){
            return new BigDecimal("1.0");
        }
        else if(!SolutionPanel.spam.getDict().containsKey(word)){
            return new BigDecimal("0.0");
        }
        System.out.println(word + "fsfs");
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
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			while((currentLine = br.readLine()) != null){
				tokens = currentLine.split("\\s");
                System.out.println("henlo");
				for(String token:tokens){
                    if(token == null) continue;

					word = token.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    accumulator = accumulator.multiply(getProbWordGivenSpam(word));
                    }
				}
			}
		} catch (IOException e){
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
				tokens = currentLine.split("\\s");
				for(String token:tokens){
                    if(token == null) continue;
					// Lowercase all . Replace all non-alphanumeric characters
					word = token.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    if(word.trim().length() > 0){
                    System.out.println(word);
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
        return 
            (computeProbabilityMessageGivenSpam().multiply(getProbSpam()))
            .divide((computeProbabilityMessageGivenSpam().multiply(getProbSpam())
                    .add(computeProbabilityMessageGivenHam().multiply(getProbHam()))), 10, RoundingMode.HALF_EVEN);            
    }
}
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Experiment{
	
	public static void main(String[] args) throws IOException{
		
		PatternMatcher[] pm = {
			new Bruteforce(),
			new BoyerMoore(),
			new KMP(),
		};

        String strtext = "testo.txt";
        Path l = Paths.get(strtext);
        String text = Files.readString(l , StandardCharsets.UTF_8);

        String strpatt = "pattern.txt";
        Path m = Paths.get(strpatt);
        String patt = Files.readString(m , StandardCharsets.UTF_8);

        for(int i=0; i<3;i++){
        	System.out.println(pm[i].getName());
        	long l1 = System.nanoTime(); 
        	int position[] = pm[i].pattmatch(text, patt);
        	long l2 = System.nanoTime();
        	if(position[0] == -1){
                System.out.println("Pattern is not matched in the text");
                System.out.println("Number of comparisons: " + position[1]);
            }else{
                System.out.println("Found at position: " + position[0]);
                System.out.println("Number of comparisons: " + position[1]);
            }  	
            System.out.println("Computation time: " + (l2 - l1)/1E6 + " ms\n");
        }
	}
}
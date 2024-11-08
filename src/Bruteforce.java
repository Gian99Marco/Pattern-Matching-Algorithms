public class Bruteforce extends PatternMatcher{
    
    public Bruteforce(){
        super("Brute Force");
    }
    
    public int[] pattmatch(String text, String pattern){
        int length = text.length();
        int plength = pattern.length();
        int count = 0; //Number of comparisons
        int solution[] = new int[2];
        solution[0] = 0;
        solution[1] = count;

        for(int i = 0; i < length-plength; i++){
            int j = 0;
            while((j < plength) && (text.charAt(i+j) == pattern.charAt(j))){
                j++;
                count++;
            }
            if(j == plength){
                solution[0] = i;
                solution[1] = count;
                return solution;
            }
        }
        solution[0] = -1;
        solution[1] = count;
        return solution;
    }
}
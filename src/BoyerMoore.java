public class BoyerMoore extends PatternMatcher{
    
    public BoyerMoore(){
        super("Boyer Moore");
    }

    public int[] pattmatch(String text, String pattern){ 
        int i = pattern.length() -1;
        int j = pattern.length() -1;
        int count = 0; //Number of comparisons
        int solution[] = new int[2];
        solution[0] = 0;
        solution[1] = count;
        do{
            if (pattern.charAt(j) == text.charAt(i)){
            	count++;
                if (j == 0){
                    solution[0]=i;
                    solution[1]=count;
                    return solution; //a match!
                }else{
                    i--;
                    j--;
                }
            }else{
                i = i + pattern.length() - min(j, 1+last(text.charAt(i), pattern)); //jump step
                j = pattern.length()-1;
            }
        }while(i <= text.length()-1);
        solution[0] = -1;
        solution[1] = count;
        return solution;
    }
    
    //----------------------------------------------------------------
    // Returns index of last occurrence of character in pattern.
    //----------------------------------------------------------------
    public static int last(char c, String P){
        for (int i=P.length()-1; i>=0; i--){
            if (P.charAt(i) == c){
                return i;
            }
        }
        return -1;
    }

    //----------------------------------------------------------------
    // Returns the minimum of two integers.
    //----------------------------------------------------------------
    public static int min(int a, int b){
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else 
            return a;
    }
}
public class KMP extends PatternMatcher{
    
    public KMP(){
        super("Knuth Morris Pratt");
    }

    public int[] pattmatch( String text, String pattern){
        int n = text.length();
        int m = pattern.length();

        int count = 0; //Number of comparisons
        int[] solution = new int[2];
        solution[0] = 0;
        solution[1] = count;

        int ff[] = new int[m];
        failureFunction(pattern, m, ff);

        int i = 0;
        int j = 0;

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                if(j == m - 1){
                    solution[0] = i-m+1;
                    solution[1]=count;
                    return solution;
                }
                i++;
                j++;
                count++;
            }else if (j > 0) {
                    j = ff[j - 1];
                }else{
                    i++;
                 }   
        }
        solution[0] = -1;
        solution[1]=count;
        return solution;
    }
  
    // length of the previous longest prefix suffix
    public void failureFunction(String pat, int m, int ff[]){
        int j = 0;
        int i = 1;
        ff[0] = 0; // ff[0] is always 0
  
        // the loop calculates ff[i] for i = 1 to m-1
        while (i < m) {
            if (pat.charAt(j) == pat.charAt(i)) {
                ff[i] = j + 1;
                i++;
                j++;
            }else if (j > 0) {
                j = ff[j - 1];
            }else {
                ff[i] = 0;
                i++;
            }
        }
    }
    
}
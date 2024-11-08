public abstract class PatternMatcher {

    protected String name;

    public PatternMatcher (String name){
        this.name=name;
    }

    public abstract int[] pattmatch(String T, String P);
    
    public String getName(){
        return name;
    }

}
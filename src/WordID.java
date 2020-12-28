
public class WordID {
	private String word;
    private int id = 0;
    
    /**CONSTRUCTORS*/

    /*Creates a new LovePoem when word is known*/
    public WordID(String word){
    	this.word = word;
    	this.id = 0;
    }
    
    
    /**ACCESORS*/
    
    /*getter word, id*/
    public String getWord() {
    	return word;
    }
    
    public int getID() {
    	return id;
    }
    
    /**MUTATORS*/
    
    /*setter word, id*/
    public void updateWord(String word) {
    	this.word = word;
    }
    
    public void updateID(int id) {
    	this.id = id;
    }
    
    /**ADDITIONAL OPERATIONS*/
    
    /**
     * Creates a String of the WordID information in the format:
     */
    @Override public String toString() {
       return "Word: " + word + "\nID: " + id + "\n";
    }
    
    /**
   	 * Compares this WordID to another WordID for equality
   	 * @return whether o is a WordID
	 * and has the same word as this WordID
   	 */
   	@Override public boolean equals(Object o) {
   		if(this==o) {
   			return true;
   		}else if(!( o instanceof WordID)) {
   			return false;
   		}else {
   			WordID w = (WordID)o;
   			if(this.word.equals(w.word)) {return true;}
   			else {return false;}
   		}
   	}
     
    /**
     * Returns a consistent hash code for
     * each word by summing the Unicode values
     * of each character in the primary key
     * Key = word
     * @return the hash code
     */
    @Override public int hashCode() {
        int sum=0;
        String key = word;
        for(int i=0;i<key.length();i++) {
        	sum+=(int)key.charAt(i);
        }
    	return sum;
    }
}

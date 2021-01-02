import java.util.Comparator;

public class LovePoem {
	private String title;
    private String author;
    private int year;
    private String country;
    private String text;
    
    private static int numPoems = 0;
    
    /**CONSTRUCTORS*/

    /*Creates a new LovePoem when only title is known*/
    public LovePoem(String title) {
    	this.title = title;
    	this.author = "unknown";
    	this.year = 0;
    	this.country= "unknown";
    	this.text = "unknown";
    	numPoems++;
    }
    
    /*Creates a new LovePoem when only title and author are known*/
    public LovePoem(String title, String author) {
    	this.title = title;
    	this.author = author;
    	this.year = 0;
    	this.country= "unknown";
    	this.text = "unknown";
    	numPoems++;
    }
    
    /*Creates a new LovePoem with no text*/
    public LovePoem(String title, String author, int year, String country) {
    	this.title = title;
    	this.author = author;
    	this.year = year;
    	this.country= country;
    	this.text = "unknown";
    	numPoems++;
    }
    
    /*Creates a new LvePoem when all information is known*/
    public LovePoem(String title, String author, int year, String country, String text) {
    	this.title = title;
    	this.author = author;
    	this.year = year;
    	this.country= country;
    	this.text = text;
    	numPoems++;
    }
    
    /**ACCESORS*/
    
    /*Accesses the total number of poems in your booklist*/
    public static int getNumPoems() {
    	return numPoems;
    }
    
    /*getter title, author, year, country, text*/
    public String getTitle() {
    	return title;
    }
    
    public String getAuthor() {
    	return author;
    }
    
    public int getYear() {
    	return year;
    }
    
    public String getCountry() {
    	return country;
    }
    
    public String getText() {
    	return text;
    }
    
    /**MUTATORS*/
    
    /*setter title, author, year, country, text*/
    public static int updateNumPoems(int i) {
    	return numPoems+=i;
    }
    
    public void updateTitle(String title) {
    	this.title = title;
    }
    
    public void updateAuthor(String author) {
    	this.author = author;
    }
    
    public void updateYear(int year) {
    	this.year = year;
    }
    
    public void updateCountry(String country) {
    	this.country = country;
    }
    
    public void updateText(String text) {
    	this.text= text;
    }
    
    /**ADDITIONAL OPERATIONS*/
    
    /**
     * Creates a String of the Poem information in the format:
     */
    @Override public String toString() {
       String result = "Title: " + title + "\nAuthor: " + author
 			  + "\nYear: " + year + "\nCountry: " + country + "\nText: " + text;  
       return result+"\n";
    }
    
    /**
   	 * Compares this Poem to another Poem for equality
   	 * @return whether o is a Poem
	 * and has the same title and author as this Poem
   	 */
   	@Override public boolean equals(Object o) {
   		if(this==o) {
   			return true;
   		}else if(!( o instanceof LovePoem)) {
   			return false;
   		}else {
   			LovePoem lp= (LovePoem)o;
   			if(this.title.equals(lp.title) && this.author.equals(lp.author)) {return true;}
   			else {return false;}
   		}
   	}
     
    /**
     * Returns a consistent hash code for
     * each Poem by summing the Unicode values
     * of each character in the primary key
     * Key = title + artist
     * @return the hash code
     */
    @Override public int hashCode() {
        int sum=0;
        String key = title + author;
        for(int i=0;i<key.length();i++) {
        	sum+=(int)key.charAt(i);
        }
    	return sum;
    }
}

class TitleComparator implements Comparator<LovePoem> {
    /**
   * Compares the two poems by title of the poem
   * @param LovePoem1 the first poem
   * @param LovePoem2 the second poem
   */
   @Override public int compare(LovePoem lp1, LovePoem lp2) {
	   return lp1.getTitle().compareTo(lp2.getTitle());
   }
}

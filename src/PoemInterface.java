import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
	
public class PoemInterface {
	final int NUM_POEMS = 10;
	HashTable<LovePoem> poems = new HashTable<>(NUM_POEMS);
	final int NUM_WORDS =1000*2 ;
	HashTable<WordID> wordToIndex = new HashTable<>(NUM_WORDS);
	static int wordIndex;
	
	ArrayList<String> uniqueKeyWords = new ArrayList<>();
	ArrayList<BST<LovePoem>> keyWordToPoems = new ArrayList<>();
	
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
			PoemInterface p =new PoemInterface();
			final String inFileName = "poems.txt";
			p.readIn(inFileName);
			System.out.println("Welcome to Love Poem System!\n");
			String choice = "";
			while(!(choice.equalsIgnoreCase("X"))){
				p.displayMenu();
				System.out.print("Enter your choice: ");
				choice = input.nextLine();				
				switch(choice.toUpperCase()) {
					case "A":{p.uploadNewPoem(); break;}
					case "B":{p.deletePoem(); break;}
					case "C":{ 
						p.displaySubMenu();
						int subChoice=0;
						while(subChoice!=1 && subChoice!=2) {
							System.out.print("Enter your choice: ");
							subChoice = input.nextInt();
							input.nextLine();
							if(subChoice==1) {p.searchAPoem();}
							else if(subChoice==2) {p.searchEngine();}
							else {System.out.println("\nInvalid menu option. Please enter 1 or 2.");}
						}
						break;
					}
					case "D":{p.updatePoem() ; break;}
					case "E":{p.displayStats(); break;}
					case "X":{p.exit();break;}
					default:{System.out.println("\nInvalid menu option. Please enter A-E or X to exit.\n");}
				}
		    }
			input.close();
	}
	
	/* Read in from a file*/
	public void readIn(String inFileName) throws IOException {	
		String title, author, country, text;
		int year;
		File inFile = new File(inFileName);
		Scanner input = new Scanner(inFile);
		while(input.hasNextLine()) {
			 title = input.nextLine();
	    	 author = input.nextLine();
	    	 year = input.nextInt();
	    	 input.nextLine();
	    	 country = input.nextLine();
	    	 text="";
	    	 String line="";
	    	 while (!line.equals("@")) {
		    	 text += line+"\n";
		    	 line = input.nextLine();
	    	 }
	      	 LovePoem lp = new LovePoem(title,author,year,country, text); 
	      	 poems.put(lp);
	      	 getKeywords(lp, text);
		}
		input.close();
	}
	
	public void getKeywords(LovePoem lp, String text) { 
		ArrayList<String> keywords = parseText(text);
		for(String keyword: keywords) {
			WordID w = new WordID(keyword);
			if(!wordToIndex.contains(w)) {
				uniqueKeyWords.add(keyword);
				w.updateID(wordIndex++);
				wordToIndex.put(w);
				BST<LovePoem> poems_title = new BST<>();
				poems_title.insert(lp, new TitleComparator());
				keyWordToPoems.add(wordToIndex.get(w).getID(), poems_title);
			}else {
				if(keyWordToPoems.get(wordToIndex.get(w).getID())
				.search(lp, false, new TitleComparator()) == null) {
					
					keyWordToPoems.get(wordToIndex.get(w).getID())
					.insert(lp, new TitleComparator());
				}
			}
		}
	}

	public ArrayList<String> parseText(String text) {
		String[] notUsefulWords = { "a", "after", "am", "an", "and", "around", "at", "with", "that", "then", "be", "before", "but", "by", "do", "every",
				"for", "from", "he", "her", "him", "i", "his", "if", "in", "is", "it", "its", "me", "my","mine", "no", "not", "of", "on", "one", "all",
				"or", "our", "she", "the", "them", "they", "to", "under", "up", "was", "we", "you","cannot", "there", "just","have","other","thou","when",
				"did","this","so","still","as","how","yours","your","can","than","ever","such","thee","yet","upon"};
		
		ArrayList<String> stopWords = new ArrayList<>();
		for(String notUsefulWord: notUsefulWords) {
			stopWords.add(notUsefulWord);
		}
		String[] words = text.replaceFirst("\\s", "").replace("\n"," ").split(" "); 
		for(int i=0;i<words.length;i++) {
			words[i] = words[i].toLowerCase().replace("'s","").replace("'ll","").replace("n't","").replace("'m","").replace("'d","").replaceAll("[^a-z]", "");
		}
		ArrayList<String> keywordsPerPoem = new ArrayList<>();
		for(String word: words) {
			if(!stopWords.contains(word)) {
				if(!keywordsPerPoem.contains(word))
					keywordsPerPoem.add(word);
			}
		}	
		return keywordsPerPoem;
	}
	
	public void displayMenu() {
		System.out.println("\nPlease select from the following options:\n");
	    System.out.println("A. Upload a new poem");
	    System.out.println("B. Delete a poem");
	    System.out.println("C. Search for a poem");
	    System.out.println("D. Update a poem");
	    System.out.println("E. Display statistics");
	    System.out.println("X. Exit");
	}
	
	public void displaySubMenu() {
	    System.out.println("\n1. Search and display a poem by title and author");
	    System.out.println("2. Search and display poem(s) by keyword");
	}
	
	/*Find and display one record using the primary key*/
	public LovePoem searchAPoem() {
		System.out.print("\nEnter the title of Poem: ");;
		String title = input.nextLine();
		System.out.print("Enter the author of Poem: ");
		String author = input.nextLine();
		LovePoem lp = new LovePoem(title, author);
		LovePoem.updateNumPoems(-1);
		if(poems.contains(lp)) {System.out.println("\n"+poems.get(lp)); return poems.get(lp);}
		else {System.out.println("\nNo poem found with this title and author"); return null;}	
	}
	
	/*Find and display records using keywords (your search engine)*/
	public void searchEngine() {
		input = new Scanner(System.in);
		System.out.print("\nEnter the keyword to search for: ");
		String enter = input.next();
		WordID keyWord = new WordID(enter.toLowerCase());
		if(!wordToIndex.contains(keyWord)) {System.out.println("\nNo poem contains the word \""+keyWord.getWord()+"\"");}
		else{
			System.out.println("\nThe following poems contain the keyword " + "\"" + keyWord.getWord() +"\":\n");
			ArrayList<LovePoem> al = new ArrayList<>();
			keyWordToPoems.get(wordToIndex.get(keyWord).getID()).inOrderPrint(al);
			for(int i=0;i<al.size();i++){
				System.out.println(i+1 + ". " + al.get(i).getTitle());
			}
			System.out.println("\nTo view more information about any of these poems, enter the number next to the poem title:");
			int index = input.nextInt()-1;
			input.nextLine();
			System.out.println("\n"+al.get(index));
		}
	}
	
	public void uploadNewPoem() throws IOException {
		String fileName="";
		File inFile= new File(fileName);
		while(!inFile.exists()) {
			System.out.println("\nEnter the file name (ending with .txt) to be uploaded: ");
			fileName =input.nextLine();
			inFile = new File(fileName);
		}
		readIn(fileName);
	}
	
	public void deletePoem() {
		System.out.println("\nEnter the title of the Poem you desire to delete: ");;
		String title = input.nextLine();
		System.out.println("Enter the author of the Poem you desire to delete: ");
		String author = input.nextLine();
		LovePoem lp = poems.get(new LovePoem(title, author));
		LovePoem.updateNumPoems(-1);
		if(poems.contains(lp)) {
			poems.remove(lp);
			ArrayList<String> keywords= parseText(lp.getText());
			for(String keyword: keywords) {
				keyWordToPoems.get(wordToIndex.get(new WordID(keyword)).getID()).remove(lp, new TitleComparator());
			}
			LovePoem.updateNumPoems(-1);
		}else {System.out.println("\nNo poem found with this title and author");}	
	}
	
	public void updatePoem() {
		System.out.println("\nEnter the original title of the Poem you desire to update: ");;
		String title = input.nextLine();
		System.out.println("Enter the original author of the Poem you desire to update: ");
		String author = input.nextLine();
		LovePoem oldLp = poems.get(new LovePoem(title, author));
		LovePoem.updateNumPoems(-1);
		if(poems.contains(oldLp)) {
			System.out.println("\nEnter the new title of the Poem:");;
			String newTitle = input.nextLine();
			System.out.println("Enter the new author of the Poem: ");
			String newAuthor = input.nextLine();
			LovePoem newLp = new LovePoem(newTitle, newAuthor,oldLp.getYear(), oldLp.getCountry(),oldLp.getText());
			LovePoem.updateNumPoems(-1);
			poems.remove(oldLp);
			poems.put(newLp);
		    ArrayList<String> keywords = parseText(newLp.getText());
			for(String keyword: keywords) {
				keyWordToPoems.get(wordToIndex.get(new WordID(keyword)).getID())
				.remove(oldLp, new TitleComparator());
				
				keyWordToPoems.get(wordToIndex.get(new WordID(keyword)).getID())
				.insert(newLp, new TitleComparator());
			}
		}else {System.out.println("\nNo poem found with this title and author");}
	}
	
	/*Display at least 3 different statistics of user choice about the data*/
	public void displayStats() {
		int max=0; 
		String mostPopular="";
		for(int i=0;i<uniqueKeyWords.size();i++) {
			int size = keyWordToPoems.get(i).getSize(); 
			if(size>max) {
				max=size; 
				mostPopular = uniqueKeyWords.get(i); 
			}
		}
		System.out.println("\nWe currently have "+ LovePoem.getNumPoems() + " poems.");
		System.out.println("\nThe poems have "+ uniqueKeyWords.size() + " unique keywords in total.");
		System.out.println("\nThe most popular keyword is \""+mostPopular+"\" which was found in " + max + " poems." );
    }	
	
	/* Write all records to a file of user choice*/
	public void exit() throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		System.out.println("\nPlease enter the file name (ending with .txt) "
				+ "where you want to write all records to: ");
		String outFileName = input.nextLine();
		writeOut(outFileName);
		System.out.println("\nAll records have been written to " + outFileName);
		System.out.println("\nGoodbye!");
		input.close();
	}
	
	/* Write out to a file*/
	public void writeOut(String outFileName) throws FileNotFoundException {
		File outFile = new File(outFileName);
		PrintWriter out = new PrintWriter(outFile);
		out.println(poems);
		out.close();
	}
}

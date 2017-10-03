//note handling
import java.util.*;

public class Notes {
	//ArrayList<ArrayList<Integer>> notes = new ArrayList<>();
	int [][] notes;
	private Random rand = new Random();
	
	//define number of frets for program to handle in parameters
	public Notes(int frets){
		/*
		for(int i=0; i < 6; i++){
			notes.add(new ArrayList<Integer>());
			for(int k=0; k <= frets; k++){
				notes.get(i).add(k);
			}
		}
		*/
		this.notes = new int[6][frets+1];
		for(int i=0; i<notes.length; i++){
			for(int k=0; k<notes[i].length; k++){
				notes[i][k] = k;
			}
		}
	}
	//both frets & strings
	public Notes(int strings, int frets){
		this.notes = new int[strings][frets+1];
		for(int i=0; i<notes.length; i++){
			for(int k=0; k<notes[i].length; k++){
				notes[i][k] = k;
			}
		}
	}
	
	//random note, no specification
	public int[] getNote(){
		int string = rand.nextInt(notes.length);
		int min = 0;
		int max = this.notes[string].length;
		int fret = rand.nextInt(max-min)+min;
		int[] note = {string,fret};
		return note;
	}
}

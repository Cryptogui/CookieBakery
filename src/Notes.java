//note handling
import java.util.*;

public class Notes {
	//ArrayList<ArrayList<Integer>> notes = new ArrayList<>();
	int [][] notes;
	
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
}

//note handling
import java.util.*;

public class Notes {
	ArrayList<ArrayList<Integer>> notes = new ArrayList<>();
	
	//define number of frets for program to handle in parameters
	public Notes(int frets){
		for(int i=0; i < 6; i++){
			notes.add(new ArrayList<Integer>());
			for(int k=0; k <= frets; k++){
				notes.get(i).add(k);
			}
		}
	}
}

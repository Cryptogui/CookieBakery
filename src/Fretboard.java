//note handling
import java.util.*;

public class Fretboard {
	int [][] notes;
	private Random rand = new Random();
	int strings, frets;
	
	//define number of frets for program to handle in parameters
	public Fretboard(int fretNum){
		frets = fretNum;
		strings = 6;
		this.notes = new int[strings][frets+1];
		for(int i=0; i<notes.length; i++){
			for(int k=0; k<notes[i].length; k++){
				notes[i][k] = k;
			}
		}
	}
	//both frets & strings
	public Fretboard(int stringNum, int fretNum){
		frets = fretNum;
		strings = stringNum;
		this.notes = new int[strings][frets+1];
		for(int i=0; i<notes.length; i++){
			for(int k=0; k<notes[i].length; k++){
				notes[i][k] = k;
			}
		}
	}
	
	int min, max, string;
	//random note, no specification
	public int[] getNote(){
		string = rand.nextInt(notes.length);
		min = 0;
		max = this.notes[string].length;
		int fret = rand.nextInt(max-min)+min;
		int[] note = {string,fret};
		return note;
	}
	//random note between fret minimum and maximum, random string
	public int[] getNote(int minimum, int maximum){
		string = rand.nextInt(notes.length);
		if(minimum<0){
			min = 0;
		} else if(minimum>this.notes[string].length) {
			min = this.notes[string].length;
		} else {
			min = minimum;
		}
		if(maximum>this.notes[string].length){
			max = this.notes[string].length;
		} else if(maximum<min) {
			max = min;
		} else {
			max = maximum;
		}
		int fret = rand.nextInt(max-min)+min;
		int[] note = {string,fret};
		return note;
	}
	//random note between fret minimum and maximum on the selected string
	public int[] getNote(int minimum, int maximum, int selectedString){
		if(0<=selectedString && selectedString<= this.notes.length){
			string = selectedString;
		} else {
			string = rand.nextInt(notes.length);
		}
		if(minimum<0){
			min = 0;
		} else if(minimum>this.notes[string].length) {
			min = this.notes[string].length;
		} else {
			min = minimum;
		}
		if(maximum>this.notes[string].length){
			max = this.notes[string].length;
		} else if(maximum<min) {
			max = min;
		} else {
			max = maximum;
		}
		int fret = rand.nextInt(max-min)+min;
		int[] note = {string,fret};
		return note;
	}
}

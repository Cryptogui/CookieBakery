import java.util.ArrayList;
import java.util.Random;

//this is where the music is created
public class Generator extends Modifiers{
	
	public Generator(){
		tab = new Tab(strings);
		notes = new int[strings][frets+1];
		for(int i=0; i<notes.length; i++){
			for(int k=0; k<notes[i].length; k++){
				notes[i][k] = k;
			}
		}
	}
	//used for generating music of selected type
	public void generate(Type a){
		switch(a){
			case ChordProgression:
				createChords();
				break;
			case Riff:
				createRiff();
				break;
			case Solo:
				createSolo();
				break;
		}
	}
	//for creating chord progressions
	private void createChords(){
		
	}
	//for creating riffs
	private void createRiff(){
		int[] newNote;
		for(int i=0; i<desiredNotes; i++){
			if(probability(100)){	//single note
				if(probability(80)){
					maxStringJump = 1;
				} else if(probability(80+15) && maxDefStringJump>=2){
					maxStringJump = 2;
				} else{
					maxStringJump = maxDefStringJump;
				}
				if(probability(95)){
					newNote = smartNote();
				} else{
					int s = rand.nextInt((lastNote[0]+maxStringJump)-(lastNote[0]-maxStringJump))+(lastNote[0]-maxStringJump);
					int fmin = lastNote[1] - maxFretJump;
					int fmax = lastNote[1] + maxFretJump;
					newNote = getNote(fmin,fmax,s);
				}
				tab.addNoteToTab(newNote);
				lastNote = newNote;
				currentNoteNumOnRow += 1;
				checkRows();
			} else{	//arpeggio
				int[][] newArpeggio = arpeggio();
				for(int[] a: newArpeggio){
					tab.addNoteToTab(a);
					lastNote = a;
					currentNoteNumOnRow += 1;
					checkRows();
				}
			}
		}
	}
	//for creating melodies over a chord progression
	private void createSolo(){
		
	}
	
	//check if number of notes in tab exceeds maximum capacity of a tab row, if so, create a new row
	int currentNoteNumOnRow = 0;
	public void checkRows(){
		if(currentNoteNumOnRow >= 15){	//maximum note capability on row is 15
			tab.createTabRow();
			currentNoteNumOnRow = 0;	//reset for the next row
		}
	}
	//returns a note that fits the current specifications (key, max fret distance from last note)
	private int[] smartNote(){
		int[] newNote;
		handleKeyNotes();
		posNewNotes.clear();	//clears the list of possible new notes
		for(int[] note: currentKeyNotes){	//for each note that belongs to the key
			if(Math.abs(note[1]-lastNote[1])<=maxFretJump && Math.abs(note[0]-lastNote[0])<=maxStringJump){	//if the note is within the maximum fret and string change distance from the last note
				posNewNotes.add(note);	//add the note to the list of possible new notes
			}
		}
		for(int i=(posNewNotes.size()-1); i>=0; i--){	//this for loop removes the notes that are not within the specified fret boundaries
			if(posNewNotes.get(i)[1]>frets){
				posNewNotes.remove(i);
			}
		}
		newNote = posNewNotes.get(rand.nextInt(posNewNotes.size()));	//get a random note from the list of possible new notes
		return newNote;
	}
	//returns an array of notes, an arpeggio
	private int[][] arpeggio(){
		int[][] arpeggio;
		ArrayList <int[][]> arp = new ArrayList<>();	//notes in the new arpeggio
		posNewNotes.clear();
		int noteCount;	//how many notes in the arpeggio?
		boolean goingUp=false,goingDown=false,arpCont = true;	//ascending,descending,does it continue?
		if(probability(50)){	//ascending?
			goingUp = true;
			for(int[] note: currentKeyNotes){	//this for loop adds the notes higher than the starting note to the list of possible notes
				if(note[0]<=lastNote[0] && lastNote[1]-Math.abs(lastNote[0]-note[0])*5<=note[1]){
					posNewNotes.add(note);
				}
			}
		} else{	//descending
			goingDown = true;
			for(int[] note: currentKeyNotes){	//this for loop adds the notes lower than the starting note to the list of possible notes
				if(note[0]>=lastNote[0] && lastNote[1]+Math.abs(lastNote[0]-note[0])*5<=note[1]){
					posNewNotes.add(note);
				}
			}
		}
		while(arpCont){
			while(goingUp){
				
			}
			while(goingDown){
				
			}
		}
		return arpeggio;
	}
	//returns true with prob % chance
	private boolean probability(int prob){	//prob should be between 1 and 100
		if(rand.nextInt(100)+1<=prob){
			return true;
		} else {
			return false;
		}
	}
	//checks which key is the current one and adds its notes to the currentKeyNotes array
	private void handleKeyNotes(){
		if(key == Key.Am || key == Key.C){
			currentKeyNotes = AmNotes;
		} else if(key == Key.Em || key == Key.G){
			currentKeyNotes = EmNotes;
		} else if(key == Key.Bm || key == Key.D){
			currentKeyNotes = BmNotes;
		} else if(key == Key.Dm || key == Key.F){
			currentKeyNotes = DmNotes;
		} else if(key == Key.Bbm || key == Key.Db){
			currentKeyNotes = BbmNotes;
		} else if(key == Key.Cm || key == Key.Eb){
			currentKeyNotes = CmNotes;
		}
	}
	
	int min, max, string;
	//random note, no specification
	public int[] getNote(){
		string = rand.nextInt(notes.length);
		min = 0;
		max = this.notes[string].length;
		int fret = rand.nextInt(max+1-min)+min;
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
		int fret = rand.nextInt(max+1-min)+min;
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
		int fret = rand.nextInt(max+1-min)+min;
		int[] note = {string,fret};
		return note;
	}
}

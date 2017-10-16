import java.util.Random;

//this is where the music is created
public class Generator extends Modifiers{
	
	public Generator(){
		fretboard = new Fretboard(strings, frets);
		tab = new Tab(fretboard);
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
				newNote = fretboard.getNote(fmin,fmax,s);
			}
			tab.addNoteToTab(newNote);
			lastNote = newNote;
			currentNoteNumOnRow += 1;
			checkRows();
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
		for(int[] note: posNewNotes){	//this for loop removes the notes that are not within the specified fret boundaries
			if(note[1]>frets){
				posNewNotes.remove(posNewNotes.indexOf(note));
			}
		}
		newNote = posNewNotes.get(rand.nextInt(posNewNotes.size()));	//get a random note from the list of possible new notes
		return newNote;
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
		}
	}
}

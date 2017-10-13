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
		for(int i=0; i<desiredNotes; i++){
			int[] newNote = fretboard.getNote();
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
		if(currentNoteNumOnRow >= 15){
			tab.createTabRow();
			currentNoteNumOnRow = 0;
		}
	}
}

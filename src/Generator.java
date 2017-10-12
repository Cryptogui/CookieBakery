//this is where the music is created
public class Generator extends Modifiers{
	
	public Generator(){
		fretboard = new Fretboard(strings);
		tab = new Tab(fretboard);
	}
	//used for generating music of selected type
	public void generate(Type a){
		switch(a){
			case ChordProgression:
				createChords();
				break;
			case Riff:
				createRiff(5);
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
	private void createRiff(int n){
		for(int i=0;i<n;i++){
			tab.addNoteToTab(fretboard.getNote());
		}
	}
	//for creating melodies over a chord progression
	private void createSolo(){
		
	}
	
	//check if number of notes in tab exceeds maximum capacity of a tab row, if so, create a new row
	public void checkRows(){
		
	}
}

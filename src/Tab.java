//used for showing/visualizing tab
//each Tab object is a complete musical idea
public class Tab {
	StringBuilder[] tab;	//contains all notes in the tab
	int currentPosition = 3;	//what location in the tab does the note have?
	public Tab(Fretboard fretboard){
		tab = new StringBuilder[fretboard.strings];
		for(int i=0; i<fretboard.strings; i++){
			tab[i] = new StringBuilder();
			for(int k=0; k<64; k++){
				if(k==0 || k==63){
					tab[i].append('|');
				} else {
					tab[i].append('-');
				}
			}
		}
	}
	
	//adds a note to the tab
	public void addNoteToTab(int[] note){
		tab[note[0]].deleteCharAt(currentPosition);
		tab[note[0]].insert(currentPosition, note[1]);
		//tab[note[0]].setCharAt(currentPosition, (char)(note[1]));
		currentPosition+=4;
	}
	
	//print tab to console
	public void print(){
		for(StringBuilder str: tab){
			System.out.println(str.toString());
		}
	}
}

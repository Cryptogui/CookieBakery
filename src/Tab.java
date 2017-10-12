//used for showing/visualizing tab
//each Tab object is a complete musical idea
public class Tab {
	StringBuilder[][] tab;	//contains all notes in the tab, tab[element representing a tab row][element representing string in the row]
	int currentPosition = 3;	//what location in the tab does the note have?
	int currentRow = 0;	//what tab row number is currently being written on
	public Tab(Fretboard fretboard){
		tab = new StringBuilder[1][fretboard.strings];
		createTabRow();
	}
	
	//adds a note to the tab
	public void addNoteToTab(int[] note){
		tab[currentRow][note[0]].deleteCharAt(currentPosition);
		tab[currentRow][note[0]].insert(currentPosition, note[1]);
		//tab[note[0]].setCharAt(currentPosition, (char)(note[1]));
		currentPosition+=4;
	}
	
	//print tab to console
	public void print(){
		for(int i=0; i<tab.length; i++){	//for every tab row
			for(StringBuilder str: tab[i]){	//for every string on the tab row
				System.out.println(str.toString());	//print the string
			}
		}
	}
	
	public void createTabRow(){
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
}

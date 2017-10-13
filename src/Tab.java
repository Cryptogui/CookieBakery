//used for showing/visualizing tab
//each Tab object is a complete musical idea

import java.util.ArrayList;

public class Tab {
	ArrayList<ArrayList<StringBuilder>> tab;	//contains all notes in the tab, tab[element representing a tab row][element representing string in the row]
	Fretboard fretboard;
	int currentPosition = 3;	//what location in the tab does the note have?
	int currentRow = 0;	//what tab row number is currently being written on
	
	public Tab(Fretboard f){
		fretboard = f;
		tab = new ArrayList<ArrayList<StringBuilder>>();
		createTabRow();
	}
	
	//adds a note to the tab
	public void addNoteToTab(int[] note){
		tab.get(currentRow).get(note[0]).deleteCharAt(currentPosition);	//deletes the char at the specified index
		tab.get(currentRow).get(note[0]).insert(currentPosition, note[1]);	//inserts the new note at that same position
		currentPosition+=4;	//moves the "marker" to the next position for the following note
	}
	
	//print tab to console
	public void print(){
		for(int i=0; i<tab.size(); i++){	//for every tab row
			for(StringBuilder str: tab.get(i)){	//for every string on the tab row
				System.out.println(str.toString());	//print the string
			}
		}
	}
	
	public void createTabRow(){
		tab.add(new ArrayList<StringBuilder>());	//creates a new tab row
		for(int i=0; i<fretboard.strings; i++){	//for each string on the fretboard
			tab.get(currentRow).add(new StringBuilder());	//add a new string on the tab
			for(int k=0; k<64; k++){
				if(k==0 || k==63){
					tab.get(currentRow).get(i).append('|');
				} else {
					tab.get(currentRow).get(i).append('-');
				}
			}
		}
		currentRow+=1;
	}
}

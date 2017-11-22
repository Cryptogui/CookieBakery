//used for showing/visualizing tab
//each Tab object is a complete musical idea

import java.util.ArrayList;

public class Tab {
	ArrayList<ArrayList<StringBuilder>> tab;	//contains all notes in the tab, tab[element representing a tab row][element representing string in the row]
	ArrayList<StringBuilder> chords;	//like tab, but for chords, chords[tab row][element representing string in the row]
	int currentPosition = 3;	//what location in the tab does the note have?
	int currentChordPosition = 3;	//what location in the tab does the chord have?
	int currentRow = 0;	//what tab row number is currently being written on
	int strings;
	Modifiers.Type type;
	
	public Tab(int stringNum, Modifiers.Type t){
		type = t;
		strings = stringNum;
		tab = new ArrayList<ArrayList<StringBuilder>>();
		chords = new ArrayList<StringBuilder>();
		switch(type){
			case Riff:
				createTabRow();
				break;
			case ChordProgression:
				createChordRow();
				break;
			case Solo:
				
				break;
		}
	}
	
	//adds a note to the tab
	public void addNoteToTab(int[] note){
		if(note[1]>=10){
			tab.get(currentRow-1).get(note[0]).deleteCharAt(currentPosition);	//deletes the char at the specified index
			tab.get(currentRow-1).get(note[0]).deleteCharAt(currentPosition);	//does it again to compensate for note with two numbers
		} else {
			tab.get(currentRow-1).get(note[0]).deleteCharAt(currentPosition);	//deletes the char at the specified index
		}
		tab.get(currentRow-1).get(note[0]).insert(currentPosition, note[1]);	//inserts the new note at that same position
		currentPosition+=4;	//moves the "marker" to the next position for the following note
	}
	
	//adds a chord to the progression
	public void addChord(String chord){
		for(int i=0; i<chord.length(); i++){
			try{	//this try/catch block handles the event that there is no space left for new chords on the chord row, in which case a new chord row is created
				chords.get(currentRow-1).deleteCharAt(currentChordPosition);	//deletes the char at the specified index
			} catch(StringIndexOutOfBoundsException e){
				createChordRow();
			}
		}
		chords.get(currentRow-1).insert(currentChordPosition, chord);	//inserts the chord at that same position
		currentChordPosition+=16;	//moves the "marker" to the next position for the following chord
	}
	
	//print tab to console
	public void print(){
		switch(type){
			case Riff:
				for(int i=0; i<tab.size(); i++){	//for every tab row
					System.out.println();	//print an empty line for visibility between tab rows
					for(StringBuilder str: tab.get(i)){	//for every string on the tab row
						System.out.println(str.toString());	//print the string
					}
				}
				break;
			case ChordProgression:
				for(int i=0; i<chords.size(); i++){	//for every chord row
					System.out.println();	//print an empty line for visibility between chord rows
					System.out.println(chords.get(i));	//print the string
				}
				break;
		default:
			break;
		}
	}
	
	//creates a new tab row, i.e. 6 empty lines like this: |-----|	where the total length of the string is 64
	public void createTabRow(){
		tab.add(new ArrayList<StringBuilder>());	//creates a new tab row
		for(int i=0; i<strings; i++){	//for each string on the fretboard
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
		currentPosition = 3;
	}
	
	//creates a new chord row, that will eventually look like this: | A   B   Cm  D |
	public void createChordRow(){
		chords.add(new StringBuilder());	//creates a new chord row
		for(int i=0; i<64; i++){
			if(i==0 || i==63){
				chords.get(currentRow).append('|');
			} else {
				chords.get(currentRow).append(' ');
			}
		}
		int halfwayIndex = chords.get(currentRow).length()/2;
		chords.get(currentRow).deleteCharAt(halfwayIndex);
		chords.get(currentRow).insert(halfwayIndex, '|');
		currentRow+=1;
		currentChordPosition = 3;
	}
}

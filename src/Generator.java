import java.util.ArrayList;
import java.util.Random;

//this is where the music is created
public class Generator extends Modifiers{

	public Generator(){
		tab = new Tab(strings, type);
		notes = new int[strings][frets+1];
		for(int i=0; i<notes.length; i++){
			for(int k=0; k<notes[i].length; k++){
				notes[i][k] = k;
			}
		}
	}
	//used for generating music of selected type
	public void generate(){
		switch(type){
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
		handleKeyNotes();
		handleKeyChords();
		String newChord, newRoot, newType;
		for(int i=0; i<desiredChords; i++){
			newType = "";
			newRoot = rootNotes.get(rand.nextInt(rootNotes.size()));
			if(minOrMaj.get(newRoot) == "major1"){
				newType = chordTypesBasicMajor1[rand.nextInt(chordTypesBasicMajor1.length)];
			} else if(minOrMaj.get(newRoot) == "major2"){
				newType = chordTypesBasicMajor2[rand.nextInt(chordTypesBasicMajor2.length)];
			} else if(minOrMaj.get(newRoot) == "minor" || minOrMaj.get(newRoot) == "diminished"){
				newType = chordTypesBasicMinor[rand.nextInt(chordTypesBasicMinor.length)];
			}
			newChord = newRoot+newType;
			tab.addChord(newChord);
		}
	}
	//for creating riffs
	private void createRiff(){
		allNotes.clear();
		int[] newNote;
		for(int i=1; i<=desiredNotes; i++){
			if(probability(80)){	//single note
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
				allNotes.add(newNote);
				tab.addNoteToTab(newNote);
				lastNote = newNote;
				currentNoteNumOnRow += 1;
				checkRows();
			} else{	//arpeggio
				int[][] newArpeggio = arpeggio(i);
				for(int[] a: newArpeggio){
					tab.addNoteToTab(a);
					lastNote = a;
					currentNoteNumOnRow += 1;
					checkRows();
				}
				i+=newArpeggio.length-1;	//-1 because the for loop already added +1
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
		newNote = smart_note_placement(newNote);
		return newNote;
	}
	//returns an array of notes, an arpeggio
	private int[][] arpeggio(int currentNoteNum){
		handleKeyNotes();
		int[][] arpeggio;	//this is the array that is returned
		ArrayList<int[]> arp = new ArrayList<>();	//notes in the new arpeggio
		ArrayList<int[]> posNextNotes = new ArrayList<>();	//used for determining the next note in the arpeggio
		int noteCount = desiredNotes-currentNoteNum;	//max possible notes in the arpeggio
		int shouldArpCont = 10;	//should the arpeggio continue? works like a timer
		boolean goingUp=false,goingDown=false,arpCont = true;	//ascending,descending,does it continue?
		if(probability(50)){	//ascending?
			goingUp = true;
		} else{	//descending
			goingDown = true;
		}
		while(arpCont && noteCount>=0){
			while(goingUp && noteCount>=0){
				posNewNotes.clear();
				posNextNotes.clear();
				for(int[] note: currentKeyNotes){	//this for loop adds the notes higher than the starting note to the list of possible notes
					if(note[0]<=lastNote[0] && lastNote[1]-Math.abs(lastNote[0]-note[0])*5<=note[1]){
						posNewNotes.add(note);
					}
				}
				for(int i=(posNewNotes.size()-1); i>=0; i--){	//this for loop removes the notes that are not within the specified fret boundaries
					if(posNewNotes.get(i)[1]>frets){
						posNewNotes.remove(i);
					}
				}
				for(int[] note: posNewNotes){	//this for loop adds the possible notes in the closest vicinity of the last note to the list of possible next notes
					if(Math.abs(lastNote[0]-note[0])<=1 && Math.abs(lastNote[1]-note[1])<=2){
						posNextNotes.add(note);
					}
				}
				int[] nextNote = posNextNotes.get(rand.nextInt(posNextNotes.size()));	//takes a random note from the list of possible next notes
				nextNote = smart_note_placement(nextNote);	//checks if there is a better placement on the fretboard for the note, e.g. (4,7) -> (3,2)
				arp.add(nextNote);	//adds the new note to the arpeggio
				allNotes.add(nextNote);
				lastNote = nextNote;	//to be able to determine the following note
				noteCount-=1;	//the arpeggio can't go on forever :(
				if(probability(shouldArpCont) || equalNotes(3)){	//should the arpeggio stop going up? (also triggers if there are too many of the same note after each other, i.e. it has reached the top string highest fret)
					if(probability(50)){	//yes, but it continues down instead
						goingUp = false;
						goingDown = true;
					} else {	//should it stop completely?
						goingUp = false;
						arpCont = false;
					}
					shouldArpCont = 10;	//reset the timer
				} else {
					shouldArpCont += 10;	//less chance of the arpeggio continuing after the next note
				}
			}
			while(goingDown && noteCount>=0){	//this while loop works similarly to the previous one
				posNewNotes.clear();
				posNextNotes.clear();
				for(int[] note: currentKeyNotes){
					if(note[0]>=lastNote[0] && lastNote[1]+Math.abs(lastNote[0]-note[0])*5>=note[1]){
						posNewNotes.add(note);
					}
				}
				for(int i=(posNewNotes.size()-1); i>=0; i--){	//this for loop removes the notes that are not within the specified fret boundaries
					if(posNewNotes.get(i)[1]>frets){
						posNewNotes.remove(i);
					}
				}
				for(int[] note: posNewNotes){
					if(Math.abs(lastNote[0]-note[0])<=1 && Math.abs(lastNote[1]-note[1])<=3){
						posNextNotes.add(note);
					}
				}
				int[] nextNote = posNextNotes.get(rand.nextInt(posNextNotes.size()));
				nextNote = smart_note_placement(nextNote);
				arp.add(nextNote);
				allNotes.add(nextNote);
				lastNote = nextNote;
				noteCount-=1;
				if(probability(shouldArpCont) || equalNotes(3)){	//should the arpeggio stop going down? (also triggers if there are too many of the same note after each other, i.e. it has reached the bottom string lowest fret)
					if(probability(50)){
						goingDown = false;
						goingUp = true;
					} else {	//should it stop completely?
						goingDown = false;
						arpCont = false;
					}
					shouldArpCont = 10;
				} else {
					shouldArpCont += 10;
				}
			}
			if(goingUp || goingDown){	//if one of these is true, start the loop from the beginning
				continue;
			} else if(!goingUp && !goingDown){	//if these are both false, then the arpeggio does not continue
				arpCont = false;;
			} else {	//stop the loop, i.e. the arpeggio does not continue
				break;
			}
		}
		arpeggio = new int[arp.size()][2];
		for(int i=0; i<arp.size(); i++){
			arpeggio[i] = arp.get(i);
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
	//finds the most suitable positioning for the new note based on previous notes
	public int[] smart_note_placement(int[] note){
		int[] newNote = note;
		if(smartNotePlacement){
			int stringMultiplier;
			try{
				if(note[1] >= allNotes.get(allNotes.size()-1)[1] && note[0] <= allNotes.get(allNotes.size()-1)[0] && note[0] != 0){	//higher fret, higher string
					if(note[0] == 2){	//is it the g string?
						stringMultiplier = 4;
					} else{	//apparently not
						stringMultiplier = 5;
					}
					if(Math.abs(note[1]-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-3)[1]) >= Math.abs(note[1]-stringMultiplier-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]-stringMultiplier-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]-stringMultiplier-allNotes.get(allNotes.size()-3)[1])){
						newNote = new int[]{note[0]-1, note[1]-stringMultiplier};
						System.out.println("("+note[0]+","+note[1]+")"+" -> "+"("+newNote[0]+","+newNote[1]+")");
					}
				} else if(note[1] < allNotes.get(allNotes.size()-1)[1] && note[0] <= allNotes.get(allNotes.size()-1)[0]){	//lower fret, higher string
					if(note[0]+1 == 2){	//is it the g string?
						stringMultiplier = 4;
					} else{	//apparently not
						stringMultiplier = 5;
					}
					if(Math.abs(note[1]-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-3)[1]) >= Math.abs(note[1]+stringMultiplier-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]+stringMultiplier-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]+stringMultiplier-allNotes.get(allNotes.size()-3)[1])){
						newNote = new int[]{note[0]+1, note[1]+stringMultiplier};
						System.out.println("("+note[0]+","+note[1]+")"+" -> "+"("+newNote[0]+","+newNote[1]+")");
					}
				} else if(note[1] < allNotes.get(allNotes.size()-1)[1] && note[0] > allNotes.get(allNotes.size()-1)[0] && note[0] != 5){	//lower fret, lower string
					if(note[0]+1 == 2){	//is it the g string?
						stringMultiplier = 4;
					} else{	//apparently not
						stringMultiplier = 5;
					}
					if(Math.abs(note[1]-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-3)[1]) >= Math.abs(note[1]+stringMultiplier-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]+stringMultiplier-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]+stringMultiplier-allNotes.get(allNotes.size()-3)[1])){
						newNote = new int[]{note[0]+1, note[1]+stringMultiplier};
						System.out.println("("+note[0]+","+note[1]+")"+" -> "+"("+newNote[0]+","+newNote[1]+")");
					}
				} else if(note[1] >= allNotes.get(allNotes.size()-1)[1] && note[0] > allNotes.get(allNotes.size()-1)[0]){	//higher fret, lower string
					if(note[0] == 2){	//is it the g string?
						stringMultiplier = 4;
					} else{	//apparently not
						stringMultiplier = 5;
					}
					if(Math.abs(note[1]-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]-allNotes.get(allNotes.size()-3)[1]) >= Math.abs(note[1]-stringMultiplier-allNotes.get(allNotes.size()-1)[1]) + Math.abs(note[1]-stringMultiplier-allNotes.get(allNotes.size()-2)[1]) + Math.abs(note[1]-stringMultiplier-allNotes.get(allNotes.size()-3)[1])){
						newNote = new int[]{note[0]-1, note[1]-stringMultiplier};
						System.out.println("("+note[0]+","+note[1]+")"+" -> "+"("+newNote[0]+","+newNote[1]+")");
					}
				}
			} catch(ArrayIndexOutOfBoundsException e){
				System.out.println("error in smart_note_placement");
			}
			if(newNote[1] > frets || newNote[0] > strings || newNote[1] < 0 || newNote[0] < 0){	//is the new note placement outside fret boundaries?
				newNote = note;
			}
		}
		return newNote;
	}
	int equalNoteCooldown = 0;	//used to prevent equalNotes() from entering loop once it returns true
	//checks if there are too many identical successive notes, if so, returns true
	public boolean equalNotes(int checkValue){
		int i=0;
		if(equalNoteCooldown>0){
			equalNoteCooldown--;
			return false;
		} else {
			while(i<=checkValue){
				try{
					if(allNotes.get(allNotes.size()-1)[0] != allNotes.get(allNotes.size()-1-i)[0] && allNotes.get(allNotes.size()-1)[1] != allNotes.get(allNotes.size()-1-i)[1]){
						return false;	//the notes are not identical
					}
				} catch(IndexOutOfBoundsException e){
					return false;	//not enough notes to determine if there are too many successive identical notes
				}
				i++;
			}
			equalNoteCooldown = 4;
			return true;	//the notes are identical
		}
	}
	//checks which key is the current one and adds its notes to the currentKeyNotes array
	private void handleKeyNotes(){
		boolean normalKey = false;
		if(key == Key.Am || key == Key.C){
			standardKeyNotes = AmNotes;
			chordKey = "C";
			normalKey = true;
		} else if(key == Key.Em || key == Key.G){
			standardKeyNotes = EmNotes;
			chordKey = "G";
			normalKey = true;
		} else if(key == Key.Bm || key == Key.D){
			standardKeyNotes = BmNotes;
			chordKey = "D";
			normalKey = true;
		} else if(key == Key.Dm || key == Key.F){
			standardKeyNotes = DmNotes;
			chordKey = "F";
			normalKey = true;
		} else if(key == Key.Bbm || key == Key.Db){
			standardKeyNotes = BbmNotes;
			chordKey = "Db";
			normalKey = true;
		} else if(key == Key.Cm || key == Key.Eb){
			standardKeyNotes = CmNotes;
			chordKey = "Eb";
			normalKey = true;
		} else if(key == Key.Fm || key == Key.Ab){
			standardKeyNotes = FmNotes;
			chordKey = "Ab";
			normalKey = true;
		}
		if(normalKey){	//is it a normal key?
			for(int i=0; i<standardKeyNotes.length; i++){
				currentKeyNotes.add(standardKeyNotes[i]);	//use the notes corresponding to that key
			}
		} else{	//it is a custom scale
			if(A){	//will the custom scale contain A?
				for(int i=0; i<ARoots.length; i++){
					currentKeyNotes.add(ARoots[i]);	//add all A roots to the custom scale
				}
			}
			if(Bb){
				for(int i=0; i<BbRoots.length; i++){
					currentKeyNotes.add(BbRoots[i]);
				}
			}
			if(B){
				for(int i=0; i<BRoots.length; i++){
					currentKeyNotes.add(BRoots[i]);
				}
			}
			if(C){
				for(int i=0; i<CRoots.length; i++){
					currentKeyNotes.add(CRoots[i]);
				}
			}
			if(Db){
				for(int i=0; i<DbRoots.length; i++){
					currentKeyNotes.add(DbRoots[i]);
				}
			}
			if(D){
				for(int i=0; i<DRoots.length; i++){
					currentKeyNotes.add(DRoots[i]);
				}
			}
			if(Eb){
				for(int i=0; i<EbRoots.length; i++){
					currentKeyNotes.add(EbRoots[i]);
				}
			}
			if(E){
				for(int i=0; i<ERoots.length; i++){
					currentKeyNotes.add(ERoots[i]);
				}
			}
			if(F){
				for(int i=0; i<FRoots.length; i++){
					currentKeyNotes.add(FRoots[i]);
				}
			}
			if(Gb){
				for(int i=0; i<GbRoots.length; i++){
					currentKeyNotes.add(GbRoots[i]);
				}
			}
			if(G){
				for(int i=0; i<GRoots.length; i++){
					currentKeyNotes.add(GRoots[i]);
				}
			}
			if(Ab){
				for(int i=0; i<AbRoots.length; i++){
					currentKeyNotes.add(AbRoots[i]);
				}
			}
		}
	}
	//determines the root notes to be used in the chord progression
	public void handleKeyChords(){
		int[] steps = {2,2,1,2,2,2};	//major scale
		int stepCounter = 0;
		int root;
		int tonic = tonics.indexOf(chordKey);
		rootNotes.add(tonics.get(tonic));	//do (I)
		minOrMaj.put(tonics.get(tonic),"major1");	//defines this root as major, so it will be used to select major type chords
		for(int nextStep: steps){	//re(II), mi(III), fa(IV), sol(V), la(VI), ti(VII)
			if(tonic+stepCounter+nextStep>=tonics.size()){
				root = tonic+stepCounter+nextStep-12;
			} else {
				root = tonic+stepCounter+nextStep;
			}
			rootNotes.add(tonics.get(root));
			stepCounter += nextStep;
			if(stepCounter == 2 || stepCounter == 4 || stepCounter == 9){	//minor
				minOrMaj.put(tonics.get(root),"minor");	//defines this root as minor, so it will be used to select minor type chords
			} else if(stepCounter == 5){	//fa (IV) major
				minOrMaj.put(tonics.get(root),"major1");	//defines this root as major, so it will be used to select major type chords, the 1 is for use of maj7 chord
			} else if(stepCounter == 7){	//sol (V) major
				minOrMaj.put(tonics.get(root),"major2");	//defines this root as major, so it will be used to select major type chords, the 2 is for use of 7 chord
			} else {	//diminished
				minOrMaj.put(tonics.get(root),"diminished");
			}
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

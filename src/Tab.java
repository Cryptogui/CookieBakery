//used for showing visualizing tab
public class Tab {
	StringBuilder[] tab;
	int currentPosition = 3;
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
		tab[note[0]].setCharAt(currentPosition, (char)(note[1]));
		currentPosition+=4;
	}
	
	//print tab to console
	public void print(){
		for(StringBuilder str: tab){
			System.out.println(str);
		}
	}
}

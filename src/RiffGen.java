import java.util.ArrayList;

public class RiffGen {
	public static void main(String[]args){
		Fretboard fretboard = new Fretboard(3);
		Tab tab = new Tab(fretboard);
		createTab(tab,fretboard,5);
		tab.print();
	}
	
	public static void createTab(Tab t, Fretboard f, int n){
		for(int i=0;i<n;i++){
			t.addNoteToTab(f.getNote());
		}
	}
}

import java.util.ArrayList;

public class RiffGen {
	public static void main(String[]args){
		Fretboard fretboard = new Fretboard(3);
		/*
		for(ArrayList i: fretboard.notes.){
			for (int k=0; k < i.size(); k++){
				System.out.println(k);
			}
		}
		*/
		for(int i=0; i<fretboard.notes.length; i++){
			for(int k: fretboard.notes[i]){
				System.out.println(k);
			}
		}
	}
}

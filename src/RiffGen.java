import java.util.ArrayList;

public class RiffGen {
	public static void main(String[]args){
		Notes fretboard = new Notes(3);
		for(ArrayList i: fretboard.notes){
			for (int k=0; k < i.size(); k++){
				System.out.println(k);
			}
		}
	}
}

import java.util.Scanner;

public class RiffGen extends Modifiers {

	public static void main(String[]args){
		Scanner scan = new Scanner(System.in);
		strings = 6;
		frets = 12;
		desiredNotes = 15;
		maxFretJump =3;
		maxDefStringJump = 2;
		key = Key.Am;
		desiredChords = 8;
		type = Type.Riff;
		//type = Type.ChordProgression;
		Generator gen = new Generator();
		//gen.generate();
		//gen.tab.print();
		
		//user interface
		while(true){
			System.out.print("command: \n");
			String input = scan.nextLine();
			if("gen".equals(input)){	//generate a new tab
				gen = new Generator();
				gen.generate();
			} else if("print".equals(input)){	//print the current tab
				gen.tab.print();
			} else if("riff".equals(input)){	//type selected for generation is riff
				type = Type.Riff;
			} else if("chords".equals(input)){	//type selected for generation is chord progression
				type = Type.ChordProgression;
			} else if("all notes".equals(input)){	//all notes in the riff
				for(int[] i: allNotes){
					System.out.print("("+i[0]+","+i[1]+")"+" , ");
				}
				System.out.println();
			} else if("exit".equals(input)){	//close the program
				break;
			}
		}
		scan.close();
	}

}
/*to do:
 *
 * tab writer
 *
 * save/export, probably i Tab class
 * 
 * chord progressions	(fixat i Tab, resten borde vara att fixa magi i Generator)
 * 
 * solo
 * 
 * different note durations?
 * 
 * anv�nda try, catch f�r ny tab/chord row ist�llet f�r en counter?		(fixat f�r chord row nu)
 *
 *nytt random object f�r varje g�ng ist�llet f�r samma rand?
 */

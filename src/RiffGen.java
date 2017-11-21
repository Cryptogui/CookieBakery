import java.util.Scanner;

public class RiffGen extends Modifiers {

	public static void main(String[]args){
		Scanner scan = new Scanner(System.in);
		strings = 6;
		frets = 12;
		desiredNotes = 16;
		maxFretJump =3;
		maxDefStringJump = 3;
		key = Key.Am;
		desiredChords = 8;
		type = Type.Riff;
		//type = Type.ChordProgression;
		Generator gen = new Generator();
		//gen.generate();
		//gen.tab.print();
		
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
 * använda try, catch för ny tab/chord row istället för en counter?		(fixat för chord row nu)
 *
 *nytt random object för varje gång istället för samma rand?
 *error med långa tabs, något med random (försvann efter att arpeggio inte utanför bounds?)
 *desirednotes inte samma som output alltid		(troligtvis i arpeggio något?)
 */

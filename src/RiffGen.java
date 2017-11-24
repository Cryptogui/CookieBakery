import java.util.Scanner;

public class RiffGen extends Modifiers {

	public static void main(String[]args){
		Scanner scan = new Scanner(System.in);
		strings = 6;
		frets = 12;
		desiredNotes = 14;
		maxFretJump =3;
		maxDefStringJump = 2;
		key = Key.Am;
		desiredChords = 8;
		type = Type.Riff;
		smartNotePlacement = true;
		Generator gen = new Generator();
		TabExporter export = new TabExporter();
		
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
			} else if("save".equals(input)){	//save the current tab
				while(true){
					System.out.print("file name: \n");
					String fileName = scan.nextLine();
					if("cancel".equals(fileName)){	//cancel save operation
						break;
					}
					System.out.print("file format: \n");
					String format = scan.nextLine();
					if("cancel".equals(format)){	//cancel save operation
						break;
					} else if("txt".equals(format)){	//save as .txt file
						export.saveToText(gen.tab, fileName);
						System.out.println("file exported");
						break;
					} else if("pdf".equals(format)){	//save as pdf file (not done yet)
						break;
					} else if("image".equals(format)){	//save as png file
						export.saveToImage(gen.tab, fileName);
						System.out.println("file exported");
						break;
					} else {
						System.out.println("format not supported");
						break;
					}
				}
			} else if("smart note placement off".equals(input)){	//don't use smart_note_placement for generation
				smartNotePlacement = false;
			} else if("smart note placement on".equals(input)){	//use smart_note_placement for generation
				smartNotePlacement = true;
			} else if("custom scale add".equals(input)){	//add note to custom scale
				while(true){
					System.out.print("add note to scale: \n");
					String note = scan.nextLine();
					if("A".equals(note)){
						A = true;
					} else if("Bb".equals(note)){
						Bb = true;
					} else if("B".equals(note)){
						B = true;
					} else if("C".equals(note)){
						C = true;
					} else if("Db".equals(note)){
						Db = true;
					} else if("D".equals(note)){
						D = true;
					} else if("Eb".equals(note)){
						Eb = true;
					} else if("E".equals(note)){
						E = true;
					} else if("F".equals(note)){
						F = true;
					} else if("Gb".equals(note)){
						Gb = true;
					} else if("G".equals(note)){
						G = true;
					} else if("Ab".equals(note)){
						Ab = true;
					} else if("cancel".equals(note)){
						break;
					}
				}
			} else if("custom scale remove".equals(input)){	//remove note from custom scale
				while(true){
					System.out.print("remove note from scale: \n");
					String note = scan.nextLine();
					if("A".equals(note)){
						A = false;
					} else if("Bb".equals(note)){
						Bb = false;
					} else if("B".equals(note)){
						B = false;
					} else if("C".equals(note)){
						C = false;
					} else if("Db".equals(note)){
						Db = false;
					} else if("D".equals(note)){
						D = false;
					} else if("Eb".equals(note)){
						Eb = false;
					} else if("E".equals(note)){
						E = false;
					} else if("F".equals(note)){
						F = false;
					} else if("Gb".equals(note)){
						Gb = false;
					} else if("G".equals(note)){
						G = false;
					} else if("Ab".equals(note)){
						Ab = false;
					} else if("cancel".equals(note)){
						break;
					}
				}
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
 */

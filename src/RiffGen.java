

public class RiffGen extends Modifiers {

	public static void main(String[]args){
		strings = 6;
		frets = 12;
		desiredNotes = 15;
		maxFretJump =3;
		maxDefStringJump = 3;
		key = Key.Am;
		time = 4/4;
		desiredChords = 12;
		//type = Type.Riff;
		type = Type.ChordProgression;
		Generator gen = new Generator();
		gen.generate();
		gen.tab.print();
	}

}
/*to do:
 *
 * tab writer
 *
 * save/export, probably i Tab class
 * 
 * chord progressions
 * 
 * solo
 * 
 * använda try, catch för ny tab/chord row istället för en counter?
 *
 *nytt random object för varje gång istället för samma rand?
 *error med långa tabs, något med random (försvann efter att arpeggio inte utanför bounds?)
 *desirednotes inte samma som output alltid
 */

//for stuff to not get in the way
abstract class Modifiers {
	public enum Type{
		ChordProgression,
		Riff,
		Solo
	}
	public enum Key{
		A,Am,Bb,Bbm,B,Bm,C,Cm,Db,Dbm,D,Dm,Eb,Ebm,E,Em,F,Fm,Gb,Gbm,G,Gm,Ab,Abm
	}
	//variables used for riff
	static int strings;	//number of strings
	static int frets;	//number of frets
	static int desiredNotes;	//number of notes to be included in the riff
	int[] lastNote;	//the latest note added to the tab
	int riffLength;	//number of notes in riff, if this exceeds maximum capacity of a tab row, a new one should be created
	//
	Fretboard fretboard;
	Tab tab;
}

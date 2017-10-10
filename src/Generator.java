//this is where the music is created
public class Generator {
	public enum Type{
		ChordProgression,
		Riff,
		Solo
	}
	public enum Key{
		A,Am,Bb,Bbm,B,Bm,C,Cm,Db,Dbm,D,Dm,Eb,Ebm,E,Em,F,Fm,Gb,Gbm,G,Gm,Ab,Abm
	}
	
	public Generator(Fretboard f, Tab t){
		
	}
	
	public void generate(Type a){
		switch(a){
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
	
	private void createChords(){
		
	}
	
	private void createRiff(){
		
	}
	
	private void createSolo(){
		
	}
}

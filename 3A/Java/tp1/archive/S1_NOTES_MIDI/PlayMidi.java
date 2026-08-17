import javax.sound.midi.* ;


    public class PlayMidi {

	final static int piano = 0 ;
	final static int tubular = 14 ;

	
    public static void main(String[] args){

	try {
	    Synthesizer syn = MidiSystem.getSynthesizer();
	    syn.open();
	    final MidiChannel[] canaux = syn.getChannels();

	    MidiChannel canalPiano = canaux[0] ;
	    canalPiano.programChange(0,piano);

	    MidiChannel canalTubular = canaux[1] ;
	    canalTubular.programChange(0,tubular);


	    canalPiano.noteOn(60,600);
	    Thread.sleep(300);
	    canalPiano.noteOn(64,600);
	    Thread.sleep(300);
	    canalTubular.noteOn(67,600);
	    Thread.sleep(4000);
	    /*  Exercice :
	     * Faire une classe Note :
	     *  * le constructeur prend la hauteur et l'instrument en paramètre
	     *  * méthode play(int) qui prend en paramètre la puissance et la durée.
	     */

	}
	catch (Exception e ){ System.exit(-1); }
	
    }
}

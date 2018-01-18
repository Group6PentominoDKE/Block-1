
public class Filled {

	private char[][][] filled;
	
	public Filled(int x, int y, int z){
		filled = new char[z+1][y+1][x+1];
		for (int z1 = 0 ; z1 < z+1; z1++)
			for (int y1 = 0 ; y1 < y+1; y1++)
				for (int x1 = 0 ; x1 < x+1; x1++) {
					filled[z1][y1][x1] = 'E';
				}
	}
	
	public Filled(char[][][] fill) {
		filled = fill;
	}
	
	public char[][][] getFilled(){
		return filled;
	}
}

package burgertime;

/**
 * @author Team #16: Elena Chong, Tayler How, and Noah Miller
 */
public class Main {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
	
		Runnable song = new Music();
		Thread songThread = new Thread(song);
		songThread.start();
		
//		String name = JOptionPane.showInputDialog("What is your name?");
		
		BurgerTimeFrame game = new BurgerTimeFrame();
//		game.setName(name);

	}
}

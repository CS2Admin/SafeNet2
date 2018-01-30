
import java.util.Scanner;
public class SafeNetRunner {



	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ATM bank = new ATM();
		
		System.out.println("SafeNet Consulting - Mr O\n" );
		
		System.out.println(bank.menu());
		String choice = input.nextLine().toUpperCase();
		while (choice.length()>0 && choice.charAt(0) != 'Q'){	//not empty and not quit
			System.out.println(bank.command(choice));
			System.out.println(bank.menu());
			choice = input.nextLine().toUpperCase();
		}
		System.out.println("\nNormal Termination");
		input.close();  //gave me a possible data leak warning - usually only did this with files
	}
	
}

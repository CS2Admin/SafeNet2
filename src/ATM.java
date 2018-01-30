

import java.util.concurrent.ConcurrentHashMap;

public class ATM {

	private ConcurrentHashMap<Integer, Integer> money;
	//This version of a HashMap should allow for better multi-thread performance
	
	public ATM(){
		money = new ConcurrentHashMap<Integer, Integer>();
		money.put(100,10);
		money.put(50,10);
		money.put(20,10);
		money.put(10,10);
		money.put(5,10);
		money.put(1,10);
	}
	
	public void reStock(){
		money.clear();
		money.put(100,10);
		money.put(50,10);
		money.put(20,10);
		money.put(10,10);
		money.put(5,10);
		money.put(1,10);
	}
	
	public String command(String toDo){
		if(toDo!=null){
			toDo = toDo.toUpperCase();
			toDo = toDo.trim();                   //remove the leading and trailing spaces
			if(toDo.length() > 0){         //Not an empty String
				toDo = toDo.trim();                   //remove the leading and trailing spaces
				switch(toDo.charAt(0)){
				case 'R':	
					reStock();
					return (balance());
				case 'I':  
					return(inventory(toDo.substring(1)));
				case 'W':
					return withDraw(toDo.substring(1)) + "\n" + balance();
					//The problem sheet does not display the balance for insufficient funds - I do
				default:
					return ("\nFailure: Invalid Command\n");
					//Throw an exception??
				}
			}
			return "Only spaces passed as a command.";
		}
		return "null String passed as a command.";
	}
	
	/*
	 * @param - temp - non-null reference to a String, non-empty
	 */
	public String inventory(String invDenominations){
		//Needed to manually remove the $ because replaceAll has issues with $
		for(int i = invDenominations.length()- 1; i >= 0; i--){
			if(invDenominations.charAt(i)=='$')
				invDenominations = invDenominations.substring(0, i) + invDenominations.substring(i+1);
		}
		invDenominations = invDenominations.trim();
		String[] denominations = invDenominations.split(" "); 

		String temp = "";
		
		for(String denom: denominations){
			denom = denom.trim();
				//test to make sure the denomination is valid
			if(money.keySet().contains(Integer.parseInt(denom)))
				temp += "\n$" + denom + " - " + money.get(Integer.parseInt(denom)) + "\n";
			else
				temp += "\n$" + Integer.parseInt(denom) + " is an invalid denomination.\n";
			

		}
		return temp;
	}

	public String withDraw(String amount){
		//Needed to manually remove the $ because replaceAll has issues with $
		for(int i = amount.length()- 1; i >= 0; i--){
			if(amount.charAt(i)=='$')
				amount = amount.substring(0, i) + amount.substring(i+1);
		}
		amount = amount.trim();
		int intAmount = Integer.parseInt(amount);
		if(!possibleWithDraw(intAmount))
			return "Failure: Insufficient funds\n";
		
		actualWithDraw(intAmount);
		return "Success: Dispensed $" + amount + "\n";
	}
	
	private boolean possibleWithDraw(int amount){
		amount -= Math.min(money.get(100), amount/100) * 100;
		amount -= Math.min(money.get(50), amount/50) * 50;
		amount -= Math.min(money.get(20), amount/20) * 20;
		amount -= Math.min(money.get(10), amount/10) * 10;
		amount -= Math.min(money.get(5), amount/5) * 5;
		amount -= Math.min(money.get(1), amount);
		if(amount==0)
			return true;
		return false;
	}
	
	private void actualWithDraw(int amount){
		int numBills = Math.min(money.get(100), amount/100);
		money.replace(100, money.get(100)-numBills);
		amount -= Math.min(money.get(100), amount/100) * 100;
		numBills = Math.min(money.get(50), amount/50);
		money.replace(50, money.get(50)-numBills);
		amount -= Math.min(money.get(50), amount/50) * 50;
		numBills = Math.min(money.get(20), amount/20);
		money.replace(20, money.get(20)-numBills);
		amount -= Math.min(money.get(20), amount/20) * 20;
		numBills = Math.min(money.get(10), amount/10);
		money.replace(10, money.get(10)-numBills);
		amount -= Math.min(money.get(10), amount/10) * 10;
		numBills = Math.min(money.get(5), amount/5);
		money.replace(5, money.get(5)-numBills);
		amount -= Math.min(money.get(5), amount/5) * 5;
		numBills = Math.min(money.get(1), amount);
		money.replace(1, money.get(1)-numBills);
	}
	
	public String balance(){
		String temp = "Machine balance:\n";
		temp += "$100 - " + money.get(100) + '\n';
		temp += "$50 - " + money.get(50) + '\n';
		temp += "$20 - " + money.get(20) + '\n';
		temp += "$10 - " + money.get(10) + '\n';
		temp += "$5 - " + money.get(5) + '\n';
		temp += "$1 - " + money.get(1) + '\n';
		
		return temp;
	}
	
	public String menu(){
		String temp = "Menu Options\n";
		temp += "R - Restock the ATM \n";
		temp += "W<dollar amount> - Withdraws that amount from the ATM(e.g. W $145)\n";
		temp += "I<denominations> - Displays the number of bills in that denomination \n";
		temp += "                   present in the ATM (e.g. I $20 $10 $1)\n";
		temp += "Q - Quit the application.\n\n";
		temp += "Command: ";
		return temp;
	}
}

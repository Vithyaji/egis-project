package core;

public class Test {

	public static void main(String[] args)  {

		//Trainer trainer = new Trainer();
		//trainer.train("C:\\Project\\sample-images\\inputs.txt","C:\\Project\\nn\\nn");
		
		Identifier identifier = new Identifier();
		String[] rng = identifier.identify("C:\\Project\\sample-images\\test8.jpg");
		System.out.println("Race "+rng[0]);
		System.out.println("gender "+rng[1]);
		
	}	

 
}

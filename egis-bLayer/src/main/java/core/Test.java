package core;

public class Test {

	public static void main(String[] args)  {

//		Trainer trainer = new Trainer();
//		trainer.train();
		
		Identifier identifier = new Identifier();
		String[] rng = identifier.identify("C:\\Project\\sample-images\\test9.jpg");
		System.out.println("Race "+rng[0]);
		
	}

}

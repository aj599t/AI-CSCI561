import java.util.*;

public class SetExample {

	public static void main(String[] args) {

		List<String> listA = new ArrayList<String>();
		listA.add("A");

		List<String> listB = new ArrayList<String>();
		listB.add("B");

		List<String> listFinal = new ArrayList<String>();
		//listFinal.addAll(listA);
		listA.addAll(listB);

		//same result
		//List<String> listFinal = new ArrayList<String>(listA);
		//listFinal.addAll(listB);

		System.out.println("listA : " + listA);
		System.out.println("listB : " + listB);
		//System.out.println("listFinal : " + listFinal);

	}

}
package edu.usc.ai;

import java.util.*;

public class SetExample {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Set<Node> s= new LinkedHashSet<Node>();
		s.add(new Node(1,"A"));
		s.add(new Node(2,"B"));
		s.add(new Node(3,"C"));
		s.add(new Node(1,"A"));*/
		
		Queue<Node> s=new PriorityQueue<Node>(new Node());
		/*q.offer("A");
		q.offer(1);
		q.offer("N");*/
		/*((LinkedList) q).push("A");
		((LinkedList) q).push("B");
		((LinkedList) q).push("C");*/
		//Set<Node> s= new LinkedHashSet<Node>();
		s.offer(new Node(1,"A",30));
		s.offer(new Node(2,"B",2));
		s.offer(new Node(3,"C",10));
		//s.offer(new Node(1,"A",4));
		
		
		
		//Object[] s1= s.toArray();
		/*System.out.println(((LinkedList) q).pop());
		System.out.println(((LinkedList) q).pop());
		((LinkedList) q).push("D");
		((LinkedList) q).push("E");*/
		System.out.println(s.poll().getG());
		System.out.println(s.poll().getG());
		System.out.println(s.poll().getG());
		System.out.println(s.poll().getG());
		
		/*Iterator i=s.iterator();
		while(i.hasNext()){
			
		}*/
		
		//System.out.println(((LinkedList) q).pop());
		
		
		/*for(int i=0;i<s1.length;i++){
			System.out.println(((Node)s1[i]).getState());
		}*/

	}

}

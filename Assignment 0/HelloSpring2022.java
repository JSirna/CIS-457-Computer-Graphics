package assignment0;

import java.util.Date;

public class HelloSpring2022 {

	public static void main(String[] args) {
		String s1 = "Hello";
        String s2 = "Spring";
        Date date = new Date();
        
        System.out.printf("'%-15s' '%S' %tY %n",s1,s2,date);
//        System.out.printf("'%S'",s2);
//        System.out.printf("%tY",date);
        
        System.out.printf("'%s' %tY",s1.concat(" "+s2),date);
	}

}

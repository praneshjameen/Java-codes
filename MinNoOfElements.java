import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
		//code
		int test,i,j,k,c,count;
		Scanner s=new Scanner(System.in);
		test=s.nextInt();
		int n1,n2;
		int n[]=new int[test];
		
		for(i=0;i<test;i++)
		{
		    count=0;
		 n1=s.nextInt();
		 n2=s.nextInt();
		int a[]=new int[n1];
		int b[]=new int[n2];
		for(j=0;j<n1;j++)
		{
		    a[j]=s.nextInt();
		}
		for(j=0;j<n2;j++)
		{
		    b[j]=s.nextInt();
		}
		for(j=0;j<n1;j++)
		{
		    c=0;
		    for(k=0;k<n2;k++)
		    {
		        if(a[j]==b[k])
		        {
		            c++;
		        }
		    }
		    if(c>0)
		    {
		       count=count+1; 
		    }
		}
		n[i]=count;
		}
		for(i=0;i<test;i++)
		{
		 System.out.println(n[i]);   
		}
	}
}

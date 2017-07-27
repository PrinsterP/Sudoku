package sudoku3x3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;

/**
 *
 * @author pprin
 */
public class SudokuSolveDiff {

    
    public int[] start(int[] Sudoku, int x, int y)
    {
        //simple 2x2 Sudoku for testing
        //every field has a number 0, 1, 2, 3, 4, 5, 5,...15 2x2 = 16 fields v[0]-v[15]
//        int[][] Sudoku=
//        {//0, 1, 2, 3
//            {0, 0, 0, 0},//0
//            {0, 4, 1, 3},//1
//            {0, 3, 0, 1},//2
//            {0, 2, 0, 0}//3
//        };
        
//        int x=2;
//        int y=2;        
        
        Store store = new Store(); //define FD store
//        int size = (x*y)*(x*y);
        IntVar[] v = new IntVar[Sudoku.length];
        for (int i = 0; i<Sudoku.length; i++)
        {
        	if (Sudoku[i]!=0)
        	{
        		v[i]=new IntVar(store, Integer.toString(i), Sudoku[i], Sudoku[i]);
        	}
        	else
        	{
        		v[i]= new IntVar(store, Integer.toString(i), 1, (x*y));
        	}
        }
//        int vpos=0;
//        for (int i = 0; i<(x*y); i++)//rows
//        {
//            for (int j=0;j<(x*y); j++)//columns
//            {
//                if(Sudoku[i][j]!=0)
//                {
//                    v[vpos]=new IntVar(store, Integer.toString(vpos), Sudoku[i][j], Sudoku[i][j]);
//                    //System.out.println(Sudoku[i][j]);
//                }
//                else
//                {
//                    v[vpos]=new IntVar(store, Integer.toString(vpos),1,(x*y));
//                }
//                vpos++;
//            }
//
//        }
        
        //every number in a row is different
        for (int i = 0; i<(x*y); i++)//rows
        {
            ArrayList<IntVar> row = new ArrayList<IntVar>(); //initialize array list
            for (int j=0;j<(x*y); j++)//columns
            {
                row.add(v[i*(x*y)+j]); //add all fields of a row
            }
            store.impose(new Alldiff(row)); //all fields of row are different

        }      
        
        //every number in a column is different
        for(int i = 0; i<(x*y);i++)//rows
        {
            ArrayList<IntVar> column = new ArrayList<IntVar>();
            for (int j=0; j<(x*y);j++)//columns
            {
                column.add(v[j*(x*y)+i]);
            }
            store.impose(new Alldiff(column));
        }
        
        //all blocks have different numbers
        for(int xb = 0; xb<x; xb++)//for x blocks
        {
            for(int yb = 0; yb<y; yb++)//for block y blocks
            {
                ArrayList<IntVar> block = new ArrayList<IntVar>();
                for(int xf = 0; xf<x; xf++)//for block x fields
                {
                    for(int yf=0; yf<y; yf++) //for y fields
                    {
                        block.add(v[((yb*y)+yf)*(x*y)+((xb*x)+xf)]);
                        //block[cblock]=((yb*y)+yf)*(x*y)+((xb*x)+xf);
                    }
                    
                }
                store.impose(new Alldiff(block));               
            }
        }
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new InputOrderSelect(store, v, new IndomainMin<IntVar>());
        boolean result = search.labeling(store,select);
        
//        if(result)
//        {
//            System.out.println("Different");
//            for(int i =0; i<size;i++)
//            {
//                System. out.print(v[i]+", ");
//            }
//        }
//        else
//        {
//            System.out.println("***************NOooooooooooo");
//        }
        //if (result) fill result int[] with result else fill result int[] with 0;
        //getting number after solving:
        //System.out.println(v[0].domain);
        int[] Solution = new int[v.length]; //initialize solution array & copy solution from v zu solution array
        
        if(result)
        {
            for(int i =0; i<v.length;i++)
            {
                Solution[i]= Integer.valueOf(v[i].domain.toString());
            }
            return Solution;
        }
        else
        {
        	return Sudoku;
        }
        
        
    }
}

package sudoku3x3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;

/**
 *
 * @author pprin
 */
public class SudokuSolveXneqY {

    
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
        
// Befüllen von IntVar
        IntVar[] v = new IntVar[Sudoku.length];
        for (int i = 0; i<Sudoku.length; i++)
        {
        	if (Sudoku[i]!=0) // if not 0 -> solver doesnt need to solve
        	{
        		v[i]=new IntVar(store, Integer.toString(i), Sudoku[i], Sudoku[i]);
        	}
        	else // if 0 -> solver has to solve from 1 - x*y (depends on sudoku size)
        	{
        		v[i]= new IntVar(store, Integer.toString(i), 1, (x*y));
        	}
        }
// Einlesen von Array
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
        
        //rules for solver
        //every row has different numbers
        for (int i = 0; i<(x*y); i++)//rows
        {
            for (int j=0;j<(x*y); j++)//columns
            {
                for(int k=j+1;k<(x*y);k++)//all fields before this in this row are already not equal, we only need fields after this field
                {
                    store.impose(new XneqY(v[i*(x*y)+j], v[i*(x*y)+k]));
                }
            }

        }      
        
        //every column has different numbers
        for(int i = 0; i<(x*y);i++)//rows
        {
            for (int j=0; j<(x*y);j++)//columns
            {
                for(int k= i+1;k<(x*y);k++)//all fields before this in this column are already not equal, we only need fields after this field
                {
                    store.impose(new XneqY(v[i*(x*y)+j],v[k*(x*y)+j]));
                }
            }
        }
        
        //blocks
        int[] block = new int[x*y];
        for(int xb = 0; xb<x; xb++)//for x blocks
        {
            for(int yb = 0; yb<y; yb++)//for block y blocks
            {
                int cblock= 0;//counter for block
                for(int xf = 0; xf<x; xf++)//for block x fields
                {
                    for(int yf=0; yf<y; yf++) //for y fields
                    {
                        //add the addresses to the block array for later not equal
                        //block[cblock]=Sudoku[(xb*x)+xf][(yb*y)+yf];
                        block[cblock]=((yb*y)+yf)*(x*y)+((xb*x)+xf); //store the addresses of the fields of a block
                        cblock++;
                        //int bb= ((yb*y)+yf)*(x*y)+((xb*x)+xf);
                        //System.out.println("x"+(xb*x)+xfs+"y"+(yb*y)+yf+"="+Sudoku[(xb*x)+xf][(yb*y)+yf]+"simple address:"+bb+"clbock ="+cblock);
                    }
                    
                }
                //when finished with block set not equal
                for(int bl=0; bl<block.length; bl++)//current address
                {
                    for (int bs=bl+1; bs<block.length; bs++)//all addresses after this one
                            {
                                store.impose(new XneqY(v[block[bl]],v[block[bs]]));
                                //System.out.println(block[bl]+"Xneq"+block[bs]);
                            }
                }
                
            }
        }
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new InputOrderSelect(store, v, new IndomainMin<IntVar>());
        boolean result = search.labeling(store,select);
        
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

package sudoku3x3;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class SudokuSolverTres {


	public static ISolver solver;
	public static int[] start(int[] Sudoku)
	{
		solver = SolverFactory.newDefault();
		solver.newVar(1000000);
		solver.setExpectedNumberOfClauses(500000);
		
		
		//Zeile
		for (int i = 1; i<= 9; i++)
		{
			//Spalte
			for (int j= 1; j<= 9; j++)
			{
				int [] literals = new int [9];
				//all fields only can have one number
				for (int k = 1; k<=9; k++)
				{
					literals[k-1] = 100 *i +10*j+k;
				}
				generateOnlyOnceClauses(literals);
			}
		}
		
		for (int i = 1; i<= 9; i++)
		{
			//every number only appears once in every column
			for (int k = 1; k<= 9; k++)
			{
				int[] literals = new int [9];
				//column
				for (int j = 1; j<= 9; j++)
				{
					literals[j-1] = 100 *i +10*j+k;
				}
				generateOnlyOnceClauses(literals);
			}
		}
		
		for (int j = 1; j<= 9; j++)
		{
			//every number only appears once every row
			for (int k = 1; k<=9; k++)
			{
				int[] literals = new int [9];
				//row
				for (int i= 1; i<= 9; i++)
				{
					literals[i-1] = 100 *i +10*j+k;
				}
				generateOnlyOnceClauses(literals);
			}
		}
		
		//blocks horizontal
		for (int i = 0; i < 3; i++)
		{
			//block vertical
			for (int j = 0; j< 3; j++)
			{
				//numbers - z
				for (int z = 1; z<=9; z++)
				{
					int[] literals = new int [9];
					int counter = 0;
					//every field of the block
					for (int k = 3 * i +1; k <= 3 * i + 3; k++)
					{
						for (int l = 3 * j + 1; l <= 3 * j + 3; l++)
						{
							literals[counter]=(100 * k) + (l * 10) + z;
							counter++;
						}
					}
					generateOnlyOnceClauses(literals);
				}
			}
		}
		
		//generate filled fields
		addSudokuClauses(Sudoku);
		
		int[] nSudoku = new int [81];		
		IProblem problem = solver;
		try {
			if (problem.isSatisfiable())
			{
				int [] model = problem.model();
				//show sudoku
//				printSudoku(model);
				nSudoku= filtermodel(model);
				for(int i = 0; i< 81; i++) nSudoku[i]= nSudoku[i]%10;
			}else
			{
				System.out.println("Unsat");
				nSudoku = Sudoku;
			}
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nSudoku;
		
	}
	private static void printSudoku(int[] model) {
		int[] matrix = filtermodel(model);
		for (int i = 0; i < 9; i++)
		{
			for (int j=0; j < 9; j++)
			{
				System.out.print(matrix[i * 9 +j ] %10  + " | ");
			}
			System.out.println("\n...................................................");
		}
		
	}
	private static int[] filtermodel(int[] model) {
		int [] ret = new int [81];
		int count = 0;
		int lastField = 0;
		for (int i = 0; i <model.length; i++)
		{
			if (count <= 81 && model[i] > 0)
			{
				if (lastField == model [i] / 10)
				{
					System.out.println ("duplicate " + lastField);
				}else
				{
					lastField = model[i] / 10;
					ret[count] = model[i];
					count++;
				}
			}
			if (count > 81 && model[i] > 0)
			{
				System.out.println("ERROR more than 1 number in one field. ");
				break;
			}
		}

		return ret;
	}
	private static void addSudokuClauses(int[] Sudoku) {
		try {
			int counter= 0;
			//Row
			for(int i =0; i < 9; i++)
			{
				//column
				for (int j = 0; j<9; j++)
					{
						
						if (Sudoku[counter] != 0)
						{
							int z = ((i+1) * 100) + ((j+1) *10) + Sudoku[counter];
//							System.out.println("input numbers: " + z);
							solver.addClause(new VecInt (new int[]{z}));
						}
						counter++;
					}

			}

			
		} catch (ContradictionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private static void generateOnlyOnceClauses(int[] literals) {
		if(literals.length != 9)
		{
			return;
		}
		for (int i = 0; i < 9; i++)
		{
			if (literals[i] / 100==0 || literals[i]/10 < 10 || literals[i] < 100)
			{
				return;
			}
		}
		//change from false to true for every possibility
		for (int i = -1; i<2; i+=2)
		{
			for (int j = -1; j<2; j+=2)
			{
				for (int k = -1; k<2; k+=2)
				{
					for (int l = -1; l<2; l+=2)
					{
						for (int m = -1; m<2; m+=2)
						{
							for (int n = -1; n<2; n+=2)
							{
								for (int o = -1; o<2; o+=2)
								{
									for (int p = -1; p<2; p+=2)
									{
										for (int q = -1; q<2; q+=2)
										{
											int []clause = new int [9];
											//8*1 + -1 = 7 ignore if 7
											if (i+j+k+l+m+n+o+p+q == 7) continue;
											clause[0]=i*literals[0];
											clause[1]=j*literals[1];
											clause[2]=k*literals[2];
											clause[3]=l*literals[3];
											clause[4]=m*literals[4];
											clause[5]=n*literals[5];
											clause[6]=o*literals[6];
											clause[7]=p*literals[7];
											clause[8]=q*literals[8];
											try {
												solver.addClause(new VecInt(clause));
											} catch (ContradictionException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}

}

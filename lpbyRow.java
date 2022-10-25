package solver;
import ilog.concert.*;
import ilog.cplex.*;
/*
 * max <(6,4,7,5),x>
 * s.t. Ax=b
 * A={{1,2,1,2},{6,5,3,2},{3,4,9,12}};
 * b={20,100,75}
 * x_i>=0
 */
public class lpbyRow {
	//按行建模
	static IloLPMatrix populateByRow(IloMPModeler model) throws IloException
	{
		IloLPMatrix lp=model.addLPMatrix();
		double[] lb= {0.0,0.0,0.0,0.0};
		double[] ub= {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
		String[] varname= {"x1","x2","x3","x4"};
		IloNumVar[] x=model.numVarArray(model.columnArray(lp,4), lb, ub,varname);
		
		double[] objvals= {6,4,7,5};
		model.addMaximize(model.scalProd(objvals, x));
    
	  double[] lhs= {-Double.MAX_VALUE,-Double.MAX_VALUE,-Double.MAX_VALUE};
		double[][] A= {{1,2,1,2},{6,5,3,2},{3,4,9,12}};
		double[] rhs= {20,100,75};
		int[][] ind= {{0,1,2,3},{0,1,2,3},{0,1,2,3}};
		lp.addRows(lhs,rhs,ind,A);
    
		return lp;
	}
	public static void main(String[] args) {
		try {
		IloCplex cplex=new IloCplex();
		IloLPMatrix lp=populateByRow(cplex);
		if(cplex.solve())
		{
			System.out.println("obj value="+cplex.getObjValue());
			double[] xval=cplex.getValues(lp);
			for(int i=0;i<4;i++)
			{
				System.out.println("x["+(i+1)+"]="+xval[i]);
			}
		}
		
		}catch(IloException ex) {
			System.out.println("Concert Error: " + ex);
		}
	}

}

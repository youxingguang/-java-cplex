package solver;
import ilog.concert.*;
import ilog.cplex.*;
/*
 * max <(6,4,7,5),x>
 * s.t. Ax<=b
 * A={{1,2,1,2},{6,5,3,2},{3,4,9,12}};
 * b={20,100,75}
 * x_i>=0
 */
public class lpbyRow {
	//按行建模
	static void populateByRow(IloMPModeler model,
			IloNumVar[][] var,
			IloRange[][] rng) throws IloException
	{
		//决策变量
		double[] lb= {0.0,0.0,0.0,0.0};//变量取值下界
		double[] ub= {Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
		String[] varname= {"x1","x2","x3","x4"};
		IloNumVar[] x=model.numVarArray(4, lb, ub,varname);
		var[0]=x;
		
		//目标函数
		double[] objvals= {6,4,7,5};
		model.addMaximize(model.scalProd(objvals, x));
		
		//约束
		rng[0]=new IloRange[3];
		double[][] A= {{1,2,1,2},{6,5,3,2},{3,4,9,12}};
		double[] b= {20,100,75};
		for(int i=0;i<3;i++)
		{
			rng[0][i]=model.addLe(model.scalProd(A[i], x), b[i]);//逐行生成约束
		}
		
		
	}
	public static void main(String[] args) {
		try {
		IloCplex cplex=new IloCplex();
		IloNumVar[][] var=new IloNumVar[1][];
		IloRange[][] rng=new IloRange[1][];
		populateByRow(cplex,var,rng);
		if(cplex.solve())
		{
			double[] x=cplex.getValues(var[0]);
			double[] dj=cplex.getReducedCosts(var[0]);//检验数
			double[] pi=cplex.getDuals(rng[0]);//对偶变量（影子价格）
			double[] slack=cplex.getSlacks(rng[0]);
			System.out.println("obj value="+cplex.getObjValue());
			for(int i=0;i<4;i++)
			{
				System.out.println("x["+(i+1)+"]="+x[i]);
			}
		}
		
		}catch(IloException ex) {
			System.out.println("Concert Error: " + ex);
		}
	}

}

package solver;
import ilog.concert.*;
import ilog.cplex.*;
/*
 * min 6*x0+6.5*x1
 * s.t. 20*x0+50*x1<=120
 *      50*x0+40*x1<=150
 *      x0,x1>=0
 */

public class lp {

	public static void main(String[] args) {
		try {
			IloCplex model=new IloCplex();//创建一个新的模型
			//model.setOut(null);
			

			//定义决策变量x0,x1
			IloNumVar[] x=new IloNumVar[2];
			for(int i=0;i<2;i++) {
				x[i]=model.numVar(0, Double.MAX_VALUE);
			}
			
			//定义约束
			 int[] coeff1= {20,50};
			 int[] coeff2= {50,40};
			 model.addLe(model.scalProd(x, coeff1), 120);
			 model.addLe(model.scalProd(x, coeff2), 150);
			
			 //定义目标函数
			double[] c= {6,6.5};
			model.addMaximize(model.scalProd(x,c));
			
			
			if (model.solve()) {
				model.output().println("Solution status = " + model.getStatus());//判断模型解的状态
				
				//最优值，最优解
				model.output().println("Solution value = " + model.getObjValue());
				double[] val= model.getValues(x);
				for (int j = 0; j < val.length; j++)
					model.output().println("x" + (j+1) + "  = " + val[j]);	
			}
			model.end();
			
		}catch (IloException e) {
				System.err.println("Concert exception caught: " + e);
			}
	}

}

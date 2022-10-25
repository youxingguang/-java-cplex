# 在java中调用cplex
## 1. cplex的参考资料
cplex是IBM旗下的产品，有提供官方参考文档：[cplex doc](https://www.ibm.com/docs/en/icos/12.8.0.0?topic=cplex) 

用户手册，[usrcplex.pdf](https://www.ibm.com/docs/en/SSSA5P_12.8.0/ilog.odms.studio.help/pdf/usrcplex.pdf)

cplex studio 软件自带参考案例  cplex\examples\src\java 
## 2.cplex的建模框架
```
import ilog.concert.*;
import ilog.cplex.*;

public class lp {

	public static void main(String[] args) {
		try {
			IloCplex model=new IloCplex();//创建一个新的模型
			//定义决策变量
			//定义约束
			 //定义目标函数
			
		}catch (IloException e) {
				System.err.println("Concert exception caught: " + e);
			}
	}

}
```
## 3.cplex求解LP,MIP,QP,QCP示例
### 3.1 cplex的三种建模方式
(1) 按行建模,参考lpbyRow.java


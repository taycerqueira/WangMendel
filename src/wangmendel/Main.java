package wangmendel;


import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;

public class Main {

	public static void main(String[] args) {
		
		//importa a base de dados ARFF utilizando classes da Weka
	    DataSource source;
		try {
			
			source = new DataSource ("base-filmes.arff");
		    Instances D = source.getDataSet();
		    //imprime informações associadas à base de dados
		    System.out.println("Num. instancias:" + D.numInstances());  
		    System.out.println("Num. atributos:" + D.numAttributes());  
		    //imprime o conteúdo da base   
		    //System.out.println("Base de Dados:");
		    //System.out.println(D.toString());
		    int quantConjuntosNebulosos = 3;
		    WangMendel wm = new WangMendel(source, quantConjuntosNebulosos);
		    wm.gerarRegras();
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}

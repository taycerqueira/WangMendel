package wangmendel;

import weka.core.converters.ConverterUtils.DataSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import weka.core.Instances;

public class Main {

	public static void main(String[] args) {
		
		long inicio = System.currentTimeMillis();
		
		//importa a base de dados ARFF utilizando classes da Weka
	    DataSource source;
	    String nomeBase = "base-filmes.arff";
	    
	    //Defina aqui a quantidade de conjuntos fuzzy que serão gerados pra cada atributo
	    int quantConjuntosNebulosos = 3;
	    
	    
		try {
			
			source = new DataSource (nomeBase);
		    Instances D = source.getDataSet();
		    //imprime informações associadas à base de dados
		    System.out.println("Quantidade de instâncias: " + D.numInstances());  
		    System.out.println("Quantidade de atributos: " + D.numAttributes()); 
		    
		    System.out.println("Executando algoritmo de Wang-Mendel. Aguarde...");
		    WangMendel wm = new WangMendel(source, quantConjuntosNebulosos);
		    ArrayList<Regra> regras = wm.gerarRegras();
		    
		    System.out.println("=> Quantidade de regras geradas: " + regras.size());
		    
		    long fim  = System.currentTimeMillis();  
		    System.out.println("Tempo de execução (min/seg): " + new SimpleDateFormat("mm:ss").format(new Date(fim - inicio)));
		    
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}

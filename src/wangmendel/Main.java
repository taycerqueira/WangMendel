package wangmendel;

import weka.core.converters.ConverterUtils.DataSource;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import weka.core.Instances;

public class Main {

	public static void main(String[] args) {
		
		long inicio = System.currentTimeMillis();
		
		//importa a base de dados ARFF utilizando classes da Weka
	    DataSource source;
	    String nomeBase = "base-filmes";
	    
	    //Defina aqui a quantidade de conjuntos fuzzy que serão gerados pra cada atributo
	    int quantConjuntosNebulosos = 3;
	    
	    
		try {
			
			source = new DataSource (nomeBase + ".arff");
		    Instances D = source.getDataSet();
		    //imprime informações associadas à base de dados
		    System.out.println("* Quantidade de instâncias: " + D.numInstances());  
		    System.out.println("* Quantidade de atributos: " + D.numAttributes());
		    System.out.println("* Quantidade de conjuntos fuzzy por atributo: " + quantConjuntosNebulosos);
		    
		    System.out.println("=> Executando algoritmo de Wang-Mendel. Aguarde...");
		    WangMendel wm = new WangMendel(source, quantConjuntosNebulosos);
		    ArrayList<Regra> regras = wm.gerarRegras();
		    
		    System.out.println("=> Quantidade de regras geradas: " + regras.size());
		    
		    long fim  = System.currentTimeMillis();  
		    System.out.println("* Tempo de execução (min/seg): " + new SimpleDateFormat("mm:ss").format(new Date(fim - inicio)));
		    
		    System.out.println("=> Gerando RULEBLOCK...");
		    
		    //Pega o nome do atributo que corresponde a classe. Aqui considero que esse atributo é sempre o último
		    String nomeClasse = source.getDataSet().attribute(D.numAttributes() - 1).name();
		    
		    
		    //Gerar arquivo contendo o RULEBLOCK para ser inserido em um arquivo .flc
		    FileWriter arquivo = new FileWriter("base-filmes.txt"); 
		    PrintWriter texto = new PrintWriter(arquivo);
		    
		    int contadorRegra = 1;
		    for (Regra regra : regras) {
		    	
		    	texto.print("RULE " + contadorRegra);
		    	texto.print(" : IF ");
		    	
		    	boolean primeiroConjunto = true;
		    	
		    	for (ConjuntoFuzzy conjunto : regra.getAntecedentes()) {
		    		
		    		if(primeiroConjunto){
		    			
			    		texto.print(conjunto.getNomeAtributo());
			    		texto.print(" IS ");
			    		texto.print(conjunto.getIdConjunto());
			    		
			    		primeiroConjunto = false;
		    			
		    		}
		    		else{
		    			
		    			texto.print(" AND ");
			    		texto.print(conjunto.getNomeAtributo());
			    		texto.print(" IS ");
			    		texto.print(conjunto.getIdConjunto());
		    			
		    		}
					

				}
		    	
		    	texto.print(" THEN ");
		    	texto.print(nomeClasse);
		    	texto.print(" IS ");
		    	texto.print(regra.getConsequente());
		    	texto.print(";\n");
		    	
		    	contadorRegra++;
				
			}
		    
		    arquivo.close();
		    System.out.println("Arquivo contendo o RULEBLOCK gerado com sucesso.");


		    
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}

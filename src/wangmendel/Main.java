package wangmendel;

import weka.core.converters.ConverterUtils.DataSource;
import java.io.FileWriter;
import java.io.IOException;
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
	    int quantConjuntosFuzzy = 3;  
	    
		try {
			
			source = new DataSource (nomeBase + ".arff");
		    Instances D = source.getDataSet();
		    //imprime informações associadas à base de dados
		    System.out.println("* Quantidade de instâncias: " + D.numInstances());  
		    System.out.println("* Quantidade de atributos: " + D.numAttributes());
		    System.out.println("* Quantidade de conjuntos fuzzy por atributo: " + quantConjuntosFuzzy);
		    
		    System.out.println("=> Executando algoritmo de Wang-Mendel. Aguarde...");
		    WangMendel wm = new WangMendel(source, quantConjuntosFuzzy);
		    ArrayList<Regra> regras = wm.gerarRegras();
		    
		    System.out.println("=> Quantidade de regras geradas: " + regras.size());
		    
		    long fim  = System.currentTimeMillis();  
		    System.out.println("* Tempo de execução (min/seg): " + new SimpleDateFormat("mm:ss").format(new Date(fim - inicio)));
		    
		    //Pega o nome do atributo que corresponde a classe. Aqui considero que esse atributo é sempre o último
		    String nomeClasse = source.getDataSet().attribute(D.numAttributes() - 1).name();
		    
		    //Gera o bloco de regras em um arquivo .txt
		    //gerarRuleblock(regras, nomeBase, nomeClasse);
		    
		    //Gera arquivo .flc (fuzzy control language)
		    gerarArquivoFlc(regras, nomeBase, nomeClasse, wm.getListaAtributos(), quantConjuntosFuzzy);   

		    
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	private static void gerarArquivoFlc(ArrayList<Regra> regras, String nomeBase, String nomeClasse, ArrayList<Atributo> listaAtributos, int quantRegioesFuzzy) throws IOException{
		
	    System.out.println("=> Gerando arquivo .flc...");
	    
	    //Gerar arquivo contendo o RULEBLOCK para ser inserido em um arquivo .flc
	    FileWriter arquivo = new FileWriter(nomeBase + ".flc"); 
	    PrintWriter texto = new PrintWriter(arquivo);
	    
	    texto.println("FUNCTION_BLOCK " + nomeBase);
	    
	    texto.println("VAR_INPUT");  
	    for(Atributo atributo : listaAtributos){
	    	
	    	//Atualmente a o JFuzzyLogic só dá suporte ao tipo REAL
	    	texto.print("\t" + atributo.getNome() + " : REAL;\n"); 
	    	
	    }
	    texto.println("END_VAR\n");
	    
	    texto.println("VAR_OUTPUT");
	    	//Atualmente a o JFuzzyLogic só dá suporte ao tipo REAL
	    	texto.print("\t" + nomeClasse + " : REAL;");
	    texto.println("\nEND_VAR\n");
	    
	    for(Atributo atributo : listaAtributos){
	    	
	    	texto.println("FUZZIFY " + atributo.getNome());
	    	//Os pontos dos conjuntos fuzzy são de pertinência triangular (3 pontos por conjunto)
	    	for (ConjuntoFuzzy conjuntoFuzzy : atributo.getConjuntosFuzzy()) {	
	    		texto.print("\t TERM ");
	    		texto.print(conjuntoFuzzy.getIdConjunto());
	    		texto.print(" := ");
	    		texto.print("(" + conjuntoFuzzy.getLimiteInferior() + ", 0) ");
	    		texto.print("(" + conjuntoFuzzy.getM() + ", 1) ");
	    		texto.print("(" + conjuntoFuzzy.getLimiteSuperior() + ", 0);\n");
			}
	    	texto.println("END_FUZZIFY\n");
	    
	    }
	    
	    texto.println("DEFUZZIFY " + nomeClasse);
	    //Termos utilizados para trabalhar com a base de filmes onde os termos da classe (polarity) são {positive, negative}
	    //Como o JFuzzyLogica só trabalha com números reais, utilizarei {1, -1}, onde 1 é positivo e -1 negativo.
	    texto.println("\t TERM positive := 1;");
	    texto.println("\t TERM negative := -1;");
	    texto.println("END_DEFUZZIFY\n");
	    
	    texto.println("RULEBLOCK No1");
	    texto.println("\t AND : MIN;"); //Use 'min' for 'and'
	    texto.println("\t ACT : MIN;"); //Use 'min' activation method
	    texto.println("\t ACCU : MAX;\n"); //Use 'max' accumulation method
	    
	    //Regras
	    int contadorRegra = 1;
	    for (Regra regra : regras) {
	    	
	    	texto.print("\t RULE " + contadorRegra);
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
	    	if(regra.getConsequente().equals("postive")){
	    		texto.print("1");
	    	}
	    	else if(regra.getConsequente().equals("negative")){
	    		texto.print("-1");
	    	}
	    	texto.print(";\n");
	    	
	    	contadorRegra++;
			
		}
	     
	    texto.println("END_RULEBLOCK\n");
	    
	    texto.println("END_FUNCTION_BLOCK");
	    
	    arquivo.close();
	    System.out.println("Arquivo .flc gerado com sucesso.");
		
	}
	
	private static void gerarRuleblock(ArrayList<Regra> regras, String nomeBase, String nomeClasse) throws IOException{
		
	    System.out.println("=> Gerando RULEBLOCK...");
	    
	    //Gerar arquivo contendo o RULEBLOCK para ser inserido em um arquivo .flc
	    FileWriter arquivo = new FileWriter(nomeBase + ".txt"); 
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
		
	}

}

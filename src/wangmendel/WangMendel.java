package wangmendel;

import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.Stats;
import java.util.ArrayList;
import java.util.List;
import weka.core.AttributeStats;
import weka.core.Instance;
import weka.core.Instances;

public class WangMendel {
	
	private DataSource dados;
	private Instances instancias;
	private int quantRegioes;
	private ArrayList<Atributo> listaAtributos;
	
	
	public WangMendel(DataSource dados, int quantRegioes) throws Exception{
		this.dados = dados;
		this.quantRegioes = quantRegioes;
		this.instancias = dados.getDataSet();
		gerarlistaAtributos();
	}
	
	public ArrayList<Regra> gerarRegras() throws Exception{
		
		ArrayList<Regra> regras = new ArrayList<Regra>();
		
		//Armazena o indice do atributo que corresponde a classe. Aqui considero que é sempre o último atributo.
		int indiceClasse = dados.getDataSet().numAttributes() - 1; 
		
		for (Instance instancia : instancias) {
			
			List<ConjuntoFuzzy> antecedentes = new ArrayList<ConjuntoFuzzy>();
			String consequente = instancia.stringValue(indiceClasse);
			
			double grauRegra = 1; //Começa com 1 por ser um fator neutro na multiplicação
			
			//Pega cada atributo da instância
			for (int i = 0; i < indiceClasse; i++) {
				
				double valor = instancia.value(i);
				Atributo atributo  = getAtributoByName(instancia.attribute(i).name());
				
				//Para cada atributo da instância, verifico o conjunto fuzzy de maior grau
				double maiorGrau = Double.NEGATIVE_INFINITY;
				ConjuntoFuzzy conjuntoMaiorGrau = null;
				
				for (ConjuntoFuzzy conjunto : atributo.getConjuntosFuzzy()) {	
					double grau = conjunto.calculaPertinencia(valor);
					if(grau > maiorGrau){
						maiorGrau = grau;
						conjuntoMaiorGrau = conjunto;
					}
				}
				
				grauRegra *= maiorGrau;
				
				//Adiciono o conjunto fuzzy de maior grau no antecedente da regra
				antecedentes.add(conjuntoMaiorGrau);
					
			}
			
			//Verfico se já existe alguma regra com os mesmos antecedentes
			int quantAtributosAntecedentes = dados.getDataSet().numAttributes() - 1;
			
			if(regras.isEmpty()){
				Regra regra = new Regra(antecedentes, consequente, grauRegra);
				regras.add(regra);	
			}
			else{

				boolean jaExiste = false;
				for (Regra r1 : regras) {
					int cont = 0;
					for(ConjuntoFuzzy a2 : antecedentes){
						for(ConjuntoFuzzy a1 : r1.getAntecedentes()){
							if(a2.getIdConjunto().equals(a1.getIdConjunto())){
								cont++;
							}
						}
					}

					//Se todos os antecedentes forem iguais...
					if(cont == quantAtributosAntecedentes){
						jaExiste = true;
						//Verifico qual regra possui maior grau: a nova regra que estou tentando criar ou a que já existe na lista
						if(grauRegra > r1.getGrau()){
							regras.remove(r1);
							Regra regra = new Regra(antecedentes, consequente, grauRegra);
							regras.add(regra);
							break;

						}
					}
					
				}
				//Se não existe nenhuma regra igual a que estou tentando inserir, adiciono ela na lista
				if(!jaExiste){
					Regra regra = new Regra(antecedentes, consequente, grauRegra);
					regras.add(regra);
				}
				
			}
			
		}

		return regras;
		
	}
	
	private void gerarlistaAtributos() throws Exception{
		
		this.listaAtributos = new ArrayList<Atributo>();
		
		//Considerando que o último atributo é sempre o atributo que corresponde a classe da instância, por isso usa-se o -1
		for (int i = 0; i <= dados.getDataSet().numAttributes() - 1; i++) {
			
			if(instancias.attribute(i).isNumeric()){
				
				AttributeStats as = instancias.attributeStats(i);
				Stats s = as.numericStats;	
				
				/*System.out.println("Atributo: " + instancias.attribute(i).name());
				System.out.println("Valor mínimo: " + s.min);
				System.out.println("Valor máximo: " + s.max);*/
				
				Atributo atributo = new Atributo(instancias.attribute(i).name(), s.min, s.max, this.quantRegioes);
				listaAtributos.add(atributo);
				
			}	
		}

	}
	
	private Atributo getAtributoByName(String nomeAtributo){
		
		Atributo atributo = null;
		
		for (Atributo a : listaAtributos) {
			if(a.getNome().equals(nomeAtributo)){
				atributo = a;
				break;
			}
			
		}
		
		return atributo;
		
	}

	public ArrayList<Atributo> getListaAtributos() {
		return listaAtributos;
	}

}

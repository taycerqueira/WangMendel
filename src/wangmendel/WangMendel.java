package wangmendel;

import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.Stats;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instance;
import weka.core.Instances;

public class WangMendel {
	
	private DataSource dados;
	private Instances instancias;
	private int quantRegioes;
	private List<Atributo> listaAtributos;
	
	
	public WangMendel(DataSource dados, int quantRegioes) throws Exception{
		this.dados = dados;
		this.quantRegioes = quantRegioes;
		this.instancias = dados.getDataSet();
		this.geralistaAtributos();
	}
	
	public void gerarRegras() throws Exception{
		
		for (Instance instancia : instancias) {
			
			for (int i = 0; i < instancia.numAttributes(); i++) {

				Attribute atributo = instancia.attribute(i);	
				
				System.out.println(atributo.name());
				
				break;
					
			}
			
			break;
			
		}
		
	}
	
	public List<ConjuntoFuzzy> getConjuntosFuzzy(int quantRegioes, String nomeAtributo, double valorMinimo, double valorMaximo) {
		
		System.out.println("=> Criando conjuntos fuzzy do atributo " + nomeAtributo);
		List<ConjuntoFuzzy> conjuntosFuzzy = new ArrayList<ConjuntoFuzzy>();
		//ConjuntoFuzzy conjunto = new ConjuntoFuzzy(atributo, limiteSuperior, limiteInferior)
		double tamanhoDominio = valorMaximo - valorMinimo;
		//System.out.println("Extensão do domínio: " + tamanhoDominio + "[" + this.limiteInferior + ", " + this.limiteSuperior + "]");
		double range = tamanhoDominio/(quantRegioes - 1);
		double inf = valorMinimo;
		double sup = valorMinimo + range;
		
		//Definição dos limites das regiões de pertinencia triangular
		for(int i = 0; i < quantRegioes; i++){
			String nomeConjunto = new String(nomeAtributo + "(" + i + ")");
			ConjuntoFuzzy conjunto = new ConjuntoFuzzy(nomeConjunto, inf, sup);
			conjuntosFuzzy.add(conjunto);
			//System.out.println("Conjunto: " + i + " [" + inf + ", " + sup + "]");
			inf += range/2;
			sup += range/2;
		}
		
		//System.out.println("TAMANHO DA LISTA DE CONJ FUZZY DO ATRIBUTO: " + conjuntosFuzzy.size());
		System.out.println("Conjuntos Fuzzy gerados com sucesso.");
		
		return conjuntosFuzzy;
	}
	
	private void geralistaAtributos() throws Exception{
		
		this.listaAtributos = new ArrayList<Atributo>();
		
		for (int i = 0; i < dados.getDataSet().numAttributes(); i++) {
			
			if(instancias.attribute(i).isNumeric()){
				
				AttributeStats as = instancias.attributeStats(i);
				Stats s = as.numericStats;	
				
				System.out.println("Atributo: " + instancias.attribute(i).name());
				System.out.println("Valor mínimo: " + s.min);
				System.out.println("Valor máximo: " + s.max);
				
				Atributo atributo = new Atributo(instancias.attribute(i).name(), s.min, s.max);
				listaAtributos.add(atributo);
				
			}	
		}		
	}

}

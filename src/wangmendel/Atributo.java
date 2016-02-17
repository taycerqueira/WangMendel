package wangmendel;

import java.util.ArrayList;
import java.util.List;
import wangmendel.ConjuntoFuzzy;

public class Atributo {
	
	private String nome;
	private double valorMaximo;
	private double valorMinimo;
	
	public Atributo(String nome, double valorMinimo, double valorMaximo){
		
		this.nome = nome;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		
	}

	public String getNome() {
		return nome;
	}

	public double getValorMaximo() {
		return valorMaximo;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public List<ConjuntoFuzzy> getConjuntosFuzzy(int quantRegioes) {
		
		//System.out.println("=> Criando conjuntos fuzzy do atributo " + this.nome);
		List<ConjuntoFuzzy> conjuntosFuzzy = new ArrayList<ConjuntoFuzzy>();
		double tamanhoDominio = this.valorMaximo - this.valorMinimo;
		//System.out.println("Extensão do domínio: " + tamanhoDominio + "[" + this.limiteInferior + ", " + this.limiteSuperior + "]");
		double range = tamanhoDominio/(quantRegioes - 1);
		double inf = this.valorMinimo;
		double sup = this.valorMinimo + range;
		
		//Definição dos limites das regiões de pertinencia triangular
		for(int i = 0; i < quantRegioes; i++){
			String idConjunto = new String(this.nome + "-" + i);
			ConjuntoFuzzy conjunto = new ConjuntoFuzzy(this.nome, idConjunto, inf, sup, i);
			conjuntosFuzzy.add(conjunto);
			//System.out.println("Conjunto: " + i + " [" + inf + ", " + sup + "]");
			inf += range/2;
			sup += range/2;
		}
		
		return conjuntosFuzzy;
	}

}

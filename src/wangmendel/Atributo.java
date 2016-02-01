package wangmendel;

import java.util.ArrayList;
import java.util.List;

import wang.mendel.ConjuntoFuzzy;

public class Atributo {
	
	private String nome;
	private double valorMaximo;
	private double valorMinimo;
	private List<ConjuntoFuzzy> conjuntosFuzzy;
	
	public Atributo(String nome, double valorMinimo, double valorMaximo){
		
		this.nome = nome;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public List<ConjuntoFuzzy> getConjuntosFuzzy(int quantRegioes) {
		
		System.out.println("=> Criando conjuntos fuzzy do atributo " + this.nome);
		List<ConjuntoFuzzy> conjuntosFuzzy = new ArrayList<ConjuntoFuzzy>();
		//ConjuntoFuzzy conjunto = new ConjuntoFuzzy(atributo, limiteSuperior, limiteInferior)
		double tamanhoDominio = this.valorMaximo - this.valorMinimo;
		//System.out.println("Extensão do domínio: " + tamanhoDominio + "[" + this.limiteInferior + ", " + this.limiteSuperior + "]");
		double range = tamanhoDominio/(quantRegioes - 1);
		double inf = this.valorMinimo;
		double sup = this.valorMinimo + range;
		
		//Definição dos limites das regiões de pertinencia triangular
		for(int i = 0; i < quantRegioes; i++){
			String nomeConjunto = new String(this.nome + "(" + i + ")");
			ConjuntoFuzzy conjunto = new ConjuntoFuzzy(nomeConjunto, inf, sup);
			this.conjuntosFuzzy.add(conjunto);
			//System.out.println("Conjunto: " + i + " [" + inf + ", " + sup + "]");
			inf += range/2;
			sup += range/2;
		}
		
		//System.out.println("TAMANHO DA LISTA DE CONJ FUZZY DO ATRIBUTO: " + conjuntosFuzzy.size());
		System.out.println("Conjuntos Fuzzy gerados com sucesso.");
		
		return this.conjuntosFuzzy;
	}

	public void setConjuntosFuzzy(List<ConjuntoFuzzy> conjuntosFuzzy) {
		this.conjuntosFuzzy = conjuntosFuzzy;
	}

}

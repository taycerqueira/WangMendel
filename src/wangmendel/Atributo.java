package wangmendel;

import java.util.ArrayList;
import java.util.List;
import wangmendel.ConjuntoFuzzy;

public class Atributo {
	
	private String nome;
	private double valorMaximo;
	private double valorMinimo;
	private ArrayList<ConjuntoFuzzy> conjuntosFuzzy;
	private int quantRegioesFuzzy;
	
	public Atributo(String nome, double valorMinimo, double valorMaximo, int quantRegioesFuzzy){
		
		this.nome = nome;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.quantRegioesFuzzy = quantRegioesFuzzy;
		
		gerarConjuntosFuzzy();
		
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

	public List<ConjuntoFuzzy> getConjuntosFuzzy() {
		return this.conjuntosFuzzy;
	}
	
	private void gerarConjuntosFuzzy(){
		
		//System.out.println("=> Criando conjuntos fuzzy do atributo " + this.nome);
		this.conjuntosFuzzy = new ArrayList<ConjuntoFuzzy>();
		double tamanhoDominio = this.valorMaximo - this.valorMinimo;
		//System.out.println("Extensão do domínio: " + tamanhoDominio + "[" + this.limiteInferior + ", " + this.limiteSuperior + "]");
		double range = tamanhoDominio/(this.quantRegioesFuzzy - 1);
		double inf = this.valorMinimo;
		double sup = this.valorMinimo + range;
		
		//Definição dos limites das regiões de pertinencia triangular
		for(int i = 0; i < this.quantRegioesFuzzy; i++){
			String idConjunto = new String(this.nome + "_" + i);
			ConjuntoFuzzy conjunto = new ConjuntoFuzzy(this.nome, idConjunto, inf, sup, i);
			this.conjuntosFuzzy.add(conjunto);
			//System.out.println("Conjunto: " + i + " [" + inf + ", " + sup + "]");
			inf += range/2;
			sup += range/2;
		}
		
	}

}

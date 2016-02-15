package wangmendel;

import java.util.List;

public class Regra {
	
	private List<ConjuntoFuzzy> antecedentes;
	private String consequente;
	private double grau;
	
	public Regra(List<ConjuntoFuzzy> antecedentes, String consequente, double grau) {
		
		this.antecedentes = antecedentes;
		this.consequente = consequente;
		this.grau = grau;
		
	}
	
	public List<ConjuntoFuzzy> getAntecedentes() {
		return antecedentes;
	}

	public String getConsequente() {
		return consequente;
	}

	public double getGrau() {
		return grau;
	}

}

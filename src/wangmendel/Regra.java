package wangmendel;

import java.util.List;

public class Regra {
	
	private List<ConjuntoFuzzy> antecedentes;
	private ConjuntoFuzzy consequente;
	private double grau;
	
	public Regra(List<ConjuntoFuzzy> antecedentes, ConjuntoFuzzy consequente, double grau) {
		
		this.antecedentes = antecedentes;
		this.consequente = consequente;
		this.grau = grau;
		
	}
	
	public List<ConjuntoFuzzy> getAntecedentes() {
		return antecedentes;
	}
	public void setAntecedentes(List<ConjuntoFuzzy> antecedentes) {
		this.antecedentes = antecedentes;
	}
	public ConjuntoFuzzy getConsequente() {
		return consequente;
	}
	public void setConsequente(ConjuntoFuzzy consequente) {
		this.consequente = consequente;
	}
	public double getGrau() {
		return grau;
	}
	public void setGrau(double grau) {
		this.grau = grau;
	}

}

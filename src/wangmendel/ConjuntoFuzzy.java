package wangmendel;

public class ConjuntoFuzzy {
	
	private String nomeAtributo;
	private double limiteInferior;
	private double limiteSuperior;
	
	public ConjuntoFuzzy(String nomeAtributo, double limiteInferior, double limiteSuperior) {
		
		this.nomeAtributo = nomeAtributo;
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
		
	}

	public String getNomeAtributo() {
		return nomeAtributo;
	}

	public void setNomeAtributo(String nomeAtributo) {
		this.nomeAtributo = nomeAtributo;
	}

	public double getLimiteInferior() {
		return limiteInferior;
	}

	public void setLimiteInferior(double limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	public double getLimiteSuperior() {
		return limiteSuperior;
	}

	public void setLimiteSuperior(double limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}
	
	//Calculo da pertinência triangular
	public double calculaPertinencia(double x){
		//System.out.println("Calculando pertinência no conjunto " + this.indiceConjunto);
		double pertinencia = 0;
		double m = (limiteSuperior + limiteInferior)/2;

		//System.out.println("Valor de x: " + x + " | Valor de m: " + m + " | Limite Inferior: " + limiteInferior + " | Limite Superior: " + limiteSuperior);
		
		if(x <= limiteInferior){
			pertinencia = 0;
		}
		else if(x > limiteInferior && x < m){
			pertinencia = (x - limiteInferior)/(m - limiteInferior);
		}
		else if(x > m && x < limiteSuperior){
			pertinencia = (limiteSuperior - x)/(limiteSuperior - m);
		}
		else if(x > limiteSuperior){
			pertinencia = 0;
		}
		//System.out.println("Pertinência calculada: " + pertinencia);
		return pertinencia;

	}

}

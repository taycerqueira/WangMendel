package wangmendel;

public class ConjuntoFuzzy {
	
	private String idConjunto;
	private double limiteInferior;
	private double limiteSuperior;
	private int indiceConjunto;
	
	public ConjuntoFuzzy(String idConjunto, double limiteInferior, double limiteSuperior, int indiceConjunto) {
		
		this.idConjunto = idConjunto;
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
		this.indiceConjunto = indiceConjunto;
		
	}

	public String getIdConjunto() {
		return idConjunto;
	}

	public double getLimiteInferior() {
		return limiteInferior;
	}

	public double getLimiteSuperior() {
		return limiteSuperior;
	}
	
	public int getIndiceConjunto() {
		return indiceConjunto;
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

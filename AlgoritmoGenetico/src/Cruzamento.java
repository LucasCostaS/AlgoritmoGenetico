import java.util.Random;

public class Cruzamento {

	private float taxaCruzamento;
	private float taxaMutacao;
	private int[][] populacao;
	private Requisitos[] requsitos;
	private Cliente[] clientes;
	private int[] score;
	Random x = new Random();
	double media = 0;
	
	public float getTaxaCruzamento() {
		return taxaCruzamento;
	}

	public void setTaxaCruzamento(float taxaCruzamento) {
		this.taxaCruzamento = taxaCruzamento;
	}

	public int[][] getPopulacao() {
		return populacao;
	}

	public void setPopulacao(int[][] populacao) {
		this.populacao = populacao;
	}

	public float getTaxaMutacao() {
		return taxaMutacao;
	}

	public void setTaxaMutacao(float taxaMutacao) {
		this.taxaMutacao = taxaMutacao;
	}
	
	
	public Requisitos[] getRequsitos() {
		return requsitos;
	}

	public void setRequsitos(Requisitos[] requsitos) {
		this.requsitos = requsitos;
	}

	public Cliente[] getClientes() {
		return clientes;
	}

	public void setClientes(Cliente[] clientes) {
		this.clientes = clientes;
	}
	
	public void calcularScore(int[][] M) {
		int[] t = new int[populacao.length];
		for (int k = 0; k < M.length; k++) {
			int s = 0;
			for (int k2 = 0; k2 <10; k2++) {
					if(M[k][k2]!=0) 
						s = s + ((requsitos[k2].getImportanciaMedia()*(4-M[k][k2]))-(requsitos[k2].getRisco()*M[k][k2]));
				}
			t[k]=s;

		}
		setScore(t);
	}
	
	public int[][] reparar(int[][] N) {
		double aux1=10000, rel1=0, rel2=0, rel3=0;
		int aux2=0;
		for(int j = 0;j<N.length;j++) {
			rel1 = 0;
			rel2 = 0;
			rel3 = 0;
			for(int i = 0;i<10;i++) {
				if(N[j][i]==1) {
					rel1 = rel1 + requsitos[i].getCusto();
				}
				else if(N[j][i]==2) {
					rel2 = rel2 + requsitos[i].getCusto();
				}
				else if(N[j][i]==3) {
					rel3 = rel3 + requsitos[i].getCusto();
				}
			}
			do {
			aux1=10000;
			if(rel1>125) {

				for (int i = 0; i < 10; i++) {
					if(N[j][i]==1) {
						if(aux1>requsitos[i].getCusto()) {
							aux1=requsitos[i].getCusto();

							aux2=i;
						}
					}
				}

					
				if((rel2+requsitos[aux2].getCusto())<125) {
					N[j][aux2]=2;
					rel1 = rel1-requsitos[aux2].getCusto();
					rel2 = rel2+requsitos[aux2].getCusto();
				}
	

				else if((rel3+requsitos[aux2].getCusto())<125) {
					N[j][aux2]=3;
					rel1 = rel1-requsitos[aux2].getCusto();
					rel3 = rel3+requsitos[aux2].getCusto();
				}
				else {
					N[j][aux2]=0;
					rel1 = rel1-requsitos[aux2].getCusto();
				}
					
			}

			aux1=10000;
			if(rel2>125) {
				for (int i = 0; i < 10; i++) {
					if(N[j][i]==2) {
						if(aux1>requsitos[i].getCusto()) {
							aux1=requsitos[i].getCusto();
							aux2=i;
						}
					}
				}



				if((rel1+requsitos[aux2].getCusto())<125) {
					N[j][aux2]=1;
					rel2 = rel2-requsitos[aux2].getCusto();
					rel1 = rel1+requsitos[aux2].getCusto();
				}


				else if((rel3+requsitos[aux2].getCusto())<125) {
					N[j][aux2]=3;
					rel2 = rel2-requsitos[aux2].getCusto();
					rel3 = rel3+requsitos[aux2].getCusto();
				}

				else {
					N[j][aux2]=0;
					rel2 = rel2-requsitos[aux2].getCusto();
				}		
			}
			aux1=10000;
			if(rel3>125) {
				for (int i = 0; i < 10; i++) {
					if(N[j][i]==3) {
						if(aux1>requsitos[i].getCusto()) {
							aux1=requsitos[i].getCusto();
							aux2=i;
						}
					}
				}



				if((rel1+requsitos[aux2].getCusto())<125) {
					N[j][aux2]=1;
					rel3 = rel3-requsitos[aux2].getCusto();
					rel1 = rel1+requsitos[aux2].getCusto();
				}
					

				else if((rel2+requsitos[aux2].getCusto())<125) {
					N[j][aux2]=2;
					rel3 = rel3-requsitos[aux2].getCusto();
					rel2 = rel2+requsitos[aux2].getCusto();
				}
					
			else {
				N[j][aux2]=0;
				rel3 = rel3-requsitos[aux2].getCusto();
			}

					
			}
			}while(rel1>125||rel2>125||rel3>125);
			
		}
		return N;
	}
	
	
	
	public int[][] Gerar(){
		int aux1 = 0, aux2 = 0, aux3 = 0, aux4 = 0, aux5 = 0;
		int[][] novaPop = new int[populacao.length][10];
		int[][] aux = new int[populacao.length][10];
		int[] scoreAux;
		int t = Math.round(populacao.length*getTaxaCruzamento());
		calcularScore(getPopulacao());
		for (int j = 0; j < t; j++) {
			aux3 = x.nextInt(8)+1;
			for (int i = 0; i < 10; i++) {
				aux1 = x.nextInt(20);
				do{
					aux2 = x.nextInt(20);
				}while(aux1==aux2);
				aux4 = x.nextInt(20);
				do{
					aux5 = x.nextInt(20);
				}while(aux4==aux5);
				if(i<aux3) {
					if(score[aux1]>score[aux2]) {
						novaPop[j][i]=populacao[aux1][i];
						if(x.nextFloat()<=getTaxaMutacao()) 
							novaPop[j][i]  = x.nextInt(4);
					}
					else {
						novaPop[j][i]=populacao[aux2][i];
						if(x.nextFloat()<=getTaxaMutacao()) 
							novaPop[j][i]  = x.nextInt(4);
					}
					
				}
				else {
					if(score[aux4]>score[aux5]) {
						novaPop[j][i]=populacao[aux4][i];
						if(x.nextFloat()<=getTaxaMutacao()) 
							novaPop[j][i]  = x.nextInt(4);
					}
					else {
						novaPop[j][i]=populacao[aux5][i];
						if(x.nextFloat()<=getTaxaMutacao()) 
							novaPop[j][i]  = x.nextInt(4);
					}
				}
			}
		}
		aux4=0;
		for (int j = t; j < populacao.length; j++) {
			aux3 =0;
			for (int i = 0; i < 10; i++) {
				if(aux3==0) {
					for (int k = 0; k < populacao.length; k++) {
						calcularScore(populacao);
						if(score[k]>aux1&&aux4!=k) {
							aux1 = score[k];
							aux2 = k;
						}
					}
					aux3++;
					aux4=aux2;
				}
				novaPop[j][i]=populacao[aux2][i];

			}
				
		}
		novaPop=reparar(novaPop);
		calcularScore(populacao);
		scoreAux=score;
		calcularScore(novaPop);
		for (int j = 0; j < populacao.length; j++) {
			aux1=10000;
			aux2=10000;
			aux3=0;
			aux4=0;
			for (int i = 0; i < populacao.length; i++) {
				if(aux1>score[i]&&score[i]!=0) {
					aux1=score[i];
					aux3=i;
				}
				if(aux2>scoreAux[i]&&scoreAux[i]!=0) {
					aux2=scoreAux[i];
					aux4=i;
				}
			}
			if(aux1<aux2) {
				for (int i = 0; i < 10; i++) {
					novaPop[aux3][i]= 0;
				}	
			}
			else {
				for (int i = 0; i < 10; i++) {
					populacao[aux4][i]= 0;
				}
			}
		}
		calcularScore(novaPop);
		for (int j = 0; j < (populacao.length/2); j++) {
			for (int i = 0; i < populacao.length; i++) {
				for (int k = 0; k < 10; k++) {
					if(score[i]>0)
						aux[j][k]=novaPop[i][k];
				}
			}
		}
		calcularScore(populacao);
		for (int j = (populacao.length/2); j < populacao.length; j++) {
			for (int i = 0; i < populacao.length; i++) {
				for (int k = 0; k < 10; k++) {
					if(score[i]>0)
						aux[j][k]=populacao[i][k];
				}
			}
		}
		return novaPop;
	}

	public int[] getScore() {
		return score;
	}

	public void setScore(int[] score) {
		this.score = score;
	}

	
	
}

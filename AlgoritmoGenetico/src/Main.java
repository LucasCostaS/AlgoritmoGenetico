import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Importancias dos clientes
		int imp1[] = {10, 8, 6, 5, 7, 8, 6, 9, 6, 10};
		int imp2[] = {10, 10, 4, 9, 7, 6, 6, 8, 7, 10};
		int imp3[] = {5, 6, 8, 1, 5, 2, 4, 3, 5, 7};	
		
		//Tamanho Geração
		int geracao = 200;
		//Tamanho População
		int tamPopulacao = 100;
		float taxaMutacao = 0.02f;
		float taxaCruzamento = 0.9f;
		int te[] = new int [tamPopulacao];
		//Criação Requisitos
		Requisitos[] r = new Requisitos[10];
		for(int i=0;i<10;i++) {
			r[i] = new Requisitos();
		}
		r[0].setCusto(60.00);
		r[0].setRisco(3);
		r[1].setCusto(40.00);
		r[1].setRisco(6);
		r[2].setCusto(40.00);
		r[2].setRisco(2);
		r[3].setCusto(30.00);
		r[3].setRisco(6);
		r[4].setCusto(20.00);
		r[4].setRisco(4);
		r[5].setCusto(20.00);
		r[5].setRisco(8);
		r[6].setCusto(25.00);
		r[6].setRisco(9);
		r[7].setCusto(70.00);
		r[7].setRisco(7);;
		r[8].setCusto(50.00);
		r[8].setRisco(6);
		r[9].setCusto(20.00);
		r[9].setRisco(6);
		
		//Clientes
		Cliente[] c = new Cliente[3];
		for(int i=0;i<3;i++) {
			c[i] = new Cliente();
		}
		c[0].setRelevancia(3);
		c[0].setImportancias(imp1);
		c[1].setRelevancia(4);	
		c[1].setImportancias(imp2);	
		c[2].setRelevancia(2);
		c[2].setImportancias(imp3);
		
		//População inicial com reparação
		int i, j;
		double rel1, rel2, rel3;
		int y[][] = new int [tamPopulacao][10];
		Random x = new Random();
		for(j = 0;j<y.length;j++) {
			rel1 = 0;
			rel2 = 0;
			rel3 = 0;
			for(i = 0;i<10;i++) {
				y[j][i] = x.nextInt(4);
				if(y[j][i]==1) {
					rel1 = rel1 + r[i].getCusto();
				}
				if(y[j][i]==2) {
					rel2 = rel2 + r[i].getCusto();
				}
				if(y[j][i]==3) {
					rel3 = rel3 + r[i].getCusto();
				}
			}
			if(rel1>125||rel2>125||rel3>125) {
				j--;
			}
		}
		
		//Importancia media dos requisitos
		for(i = 0;i<10;i++) {
			r[i].setImportanciaMedia((c[0].getRelevancia()*imp1[i])+(c[1].getRelevancia()*imp2[i])+(c[2].getRelevancia()*imp3[i]));
		}
		
		//Cruzamento
		Cruzamento cr = new Cruzamento();
		cr.setPopulacao(y);
		cr.setTaxaCruzamento(taxaCruzamento);
		cr.setTaxaMutacao(taxaMutacao);
		cr.setRequsitos(r);
		cr.setClientes(c);
		int z =0;
		double media = 0;
		int aux = 0;
		do {
			for (int k = 0; k < y.length; k++) {
				media=0;
				cr.calcularScore(y);
				te=cr.getScore();
				for (int k2 = 0; k2 < y.length; k2++) {
					media+=te[k2];
					if(aux<te[k2])
						aux=te[k2];
				}
				
			}
			System.out.println(media/tamPopulacao+"\t"+aux);
			y=cr.Gerar();
			cr.setPopulacao(y);
			z++;
		}while(z<geracao);
	}
}

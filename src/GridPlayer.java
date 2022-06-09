import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GridPlayer extends Grid{

    private Scanner scan = new Scanner(System.in);
    public GridPlayer(Map<String, Linha> linhas) {

        super(linhas);

    }

    public GridPlayer(Map<String, Linha> linhas, Navio[] frota) {

        super(linhas, frota);

    }

    public void listarGrid() {

        System.out.println("     1     2     3     4     5     6     7     8     9     10");
        System.out.println("  -------------------------------------------------------------");
        
        for(Entry<String, Linha> entry : linhas.entrySet()) {

            System.out.print(entry.getKey() + " |");

            for(Quadrado quadrado : entry.getValue().getQuadrados()) {

                if(quadrado.getNavio() == true && quadrado.getAtingido() == true) {

                    System.out.printf(" %2s  |","💥"); 

                } else if (quadrado.getNavio() == true) {
                    //
                    System.out.printf(" %2s  |","🚢"); 

                } else if (quadrado.getAtingido() == true){

                    System.out.printf(" %2s  |", "💣");

                } else {

                    System.out.printf(" %2s  |", "🌊");

                }

            }
            
            System.out.println();
            System.out.println("  -------------------------------------------------------------");

        }

    }

    public boolean listarPossibilidades(int coluna, int linha, Navio navio) {

        int x = 1;
        int tamanho = navio.getTamanho();
        tamanho--;
        Map<Integer, ArrayList<Quadrado>> possibilidades = new HashMap<>(); 
        ArrayList<Quadrado> disponiveis = new ArrayList<>();

        

        if (tamanho == 0) {

            disponiveis = checarDisponibilidade(coluna, coluna, linha, linha);
            if(disponiveis != null) {
                posicionarNavio(navio, disponiveis);
                return true;
            }

        } else {

            if ((coluna - tamanho) > 0) {
                disponiveis = checarDisponibilidade(coluna-tamanho, coluna, linha, linha);
                if (disponiveis != null) {

                    System.out.printf("(%d) Esquerda%n", x);
                    possibilidades.put(x,disponiveis);
                    x++;

                }
                

            }

            if ((coluna + tamanho) < 10) {

                disponiveis = checarDisponibilidade(coluna, coluna+tamanho, linha, linha);
                if (disponiveis != null) {

                    System.out.printf("(%d) Direita%n", x);
                    
                    possibilidades.put(x,disponiveis);
                    x++;

                }

            }

            if ((linha - tamanho) > 0) {

                disponiveis = checarDisponibilidade(coluna, coluna, linha-tamanho, linha);
                if (disponiveis != null) {

                    System.out.printf("(%d) Cima%n", x);
                    possibilidades.put(x,disponiveis);
                    x++;

                }

            }

            if ((linha + tamanho) < 10) {

                disponiveis = checarDisponibilidade(coluna, coluna, linha, linha+tamanho);
                if (disponiveis != null) {

                    System.out.printf("(%d) Baixo%n", x);
                    possibilidades.put(x,disponiveis);
                    x++;
                }

            }

            if (x > 1) {

               // int opt = scan.nextInt();
                //posicionarNavio(navio, possibilidades.get(opt));
                escolherPossibilidade(navio, possibilidades);
                return true;

            } else {
                System.out.println("Não há posição disponível");
                return false;
            }

        }

        return false;

    }

    public void colocarNavios() {

        //escolherLocalizacao(frota[0]);
        
        for (int i = 0; i < frota.length; i++) {
            listarGrid();
            escolherLocalizacao(frota[i]);
        }
        
        listarGrid();
    }

    
    public void escolherPossibilidade(Navio navio, Map<Integer, ArrayList<Quadrado>> possibilidades) {

        int opt = scan.nextInt();
        posicionarNavio(navio, possibilidades.get(opt));

    }
    

    public void escolherLocalizacao(Navio navio) {

        String linhaLetra;
        int linha;
        int coluna = 0;

        do {
            
            if(!navio.getNome().equals("Encouraçado")) {
                scan.nextLine();
            }
  
            System.out.println("Onde você deseja colocar o "+ navio.getNome()+"?");
            do {

                System.out.print("Linha: ");
                linhaLetra = scan.nextLine();

            } while(!Converter.checarValidade(linhaLetra));
            
            boolean errado = true;

            do {

                try {

                    System.out.print("Coluna: ");
                    coluna = Integer.parseInt(scan.nextLine());
                    errado = false;

                } catch(Exception e) {

                }

            } while (errado && coluna < 1 && coluna > 10);
            
            
            
            //scan.skip("[\r\n]+");
            linha = Converter.stringToInt(linhaLetra);

        } while (!listarPossibilidades(coluna, linha, navio));

    }

    
    
    

    
}

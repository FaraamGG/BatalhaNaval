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

    public void listarGrid() {

        System.out.println("     1     2     3     4     5     6     7     8     9     10");

        for(Entry<String, Linha> entry : linhas.entrySet()) {

            System.out.print(entry.getKey() + " |");

            for(Quadrado quadrado : entry.getValue().getQuadrados()) {

                if(quadrado.getNavio() == true && quadrado.getAtingido() == true) {

                    System.out.printf(" %2s  |","💥"); 

                } else if (quadrado.getNavio() == true) {
                    
                    System.out.printf(" %2s  |","🚢"); 

                } else if (quadrado.getAtingido() == true){

                    System.out.printf(" %2s  |", "⚔");

                } else {

                    System.out.printf(" %2s  |", "≋");

                }

            }

            System.out.println();

        }

    }

    public void colocarNavios() {

        //escolherLocalizacao(frota[0]);
        
        for (int i = 0; i < frota.length; i++) {
            listarGrid();
            escolherLocalizacao(frota[i]);
        }
        
    }

    
    public void escolherPossibilidade(Navio navio, Map<Integer, ArrayList<Quadrado>> possibilidades) {

        int opt = scan.nextInt();
        posicionarNavio(navio, possibilidades.get(opt));

    }
    

    public void escolherLocalizacao(Navio navio) {

        String linhaLetra;
        int linha;
        int coluna;

        do {
            
            if(!navio.getNome().equals("Encouraçado")) {
                scan.nextLine();
            }
  
            System.out.println("Onde você deseja colocar o "+ navio.getNome()+"?");
            System.out.print("Linha: ");
            linhaLetra = scan.nextLine();
            System.out.print("Coluna: ");
            coluna = Integer.parseInt(scan.nextLine());
            
            //scan.skip("[\r\n]+");
            linha = Converter.stringToInt(linhaLetra);

        } while (!listarPossibilidades(coluna, linha, navio));

    }

    
    
    

    
}

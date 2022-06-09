import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class GridInimiga extends Grid{

    private SecureRandom random = new SecureRandom();

    public GridInimiga(Map<String, Linha> linhas) {

        super(linhas);

    }

    public void listarGrid() {

        System.out.println("     1     2     3     4     5     6     7     8     9     10");
        System.out.println("  -------------------------------------------------------------");

        for(Entry<String, Linha> entry : linhas.entrySet()) {

            System.out.print(entry.getKey() + " |");

            for(Quadrado quadrado : entry.getValue().getQuadrados()) {

                if(quadrado.getNavio() == true && quadrado.getAtingido() == true) {

                    System.out.printf(" %2s  |","💥"); 

                } else if (quadrado.getAtingido() == true){

                    System.out.printf(" %2s  |", "⚔");

                } else {

                    System.out.printf(" %2s  |", "≋");

                }

            }

            System.out.println();
            System.out.println("  -------------------------------------------------------------");

        }

    }

    public void escolherLocalizacao(Navio navio) {

        int linha;
        int coluna;

        do {

            linha = 1 + random.nextInt(10);
            coluna = 1 + random.nextInt(10);

        } while (!listarPossibilidades(coluna, linha, navio));

    }

    @Override
    public void escolherPossibilidade(Navio navio, Map<Integer, ArrayList<Quadrado>> possibilidades) {

        int opt = 1 + random.nextInt(possibilidades.size());
        posicionarNavio(navio, possibilidades.get(opt));

    }
    
}

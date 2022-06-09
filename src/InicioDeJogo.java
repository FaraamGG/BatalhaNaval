import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InicioDeJogo {

    Scanner scan = new Scanner(System.in);
    Grid gridAliada;
    Grid gridInimiga;
    IA ia;
    int turno;

    public InicioDeJogo() {

        turno = 0;
        
        criarGrids();
        posicionarNavios();
        ia = new IA(gridAliada);
        
        combate();

    }

    public void mostrarGrids() {

        System.out.println("Sua grid");

    }

    public void combate() {

        System.out.println();
        System.out.println("Inicio do combate");
        System.out.println();

        while(turno < 100) {

            System.out.println("TURNO: " + turno);
            System.out.println("Seu turno: ");
            System.out.println("Grid inimiga");
            gridInimiga.listarGrid();
            playerAtaque();
            gridInimiga.listarGrid();
            if (checarVidaInimiga()) {
                System.out.println("Fim de jogo");
                break;
            }

            System.out.println();
            System.out.println();

            System.out.println("Turno inimigo: ");
            ia.atirar();
            gridAliada.listarGrid();
            turno++;
            if (checarVidaAliada()) {
                System.out.println("Fim de jogo");
                break;
            }
            

        }


    }

    public boolean checarVidaAliada() {

        boolean vivo = true;

        for (Navio navio : gridAliada.getNavios()) {

            if(navio.getVida() > 0) {
                vivo = false;
            }
            
        }

        return vivo;

    }

    public boolean checarVidaInimiga() {

        boolean vivo = true;

        for (Navio navio : gridInimiga.getNavios()) {

            if(navio.getVida() > 0) {
                vivo = false;
            }
            
        }

        return vivo;

    }

    public void playerAtaque() {

        System.out.println("Onde você deseja atacar?");

        String linha;
        int coluna;
        boolean valido;

        do {

            if(turno != 0)
                scan.nextLine();

            System.out.print("Linha: ");
            linha = scan.nextLine();
            System.out.print("Coluna: ");
            coluna = scan.nextInt();

            valido = gridInimiga.quadradoFoiAtingido(linha, coluna);

        } while (valido);

        gridInimiga.atirar(linha, coluna);

    }

    public void posicionarNavios() {

        gridAliada.colocarNavios();
        gridInimiga.colocarNavios();

    }

    public Grid getGridInimiga() {

        return gridInimiga;

    }

    public Grid getGridAliada() {

        return gridAliada;
        
    }

    private void criarGrids() {

        criarGridPlayer();
        criarGridComputador();

    }

    private void criarGridPlayer() {

        Map<String, Linha> linhas = new HashMap<>();

        for(int i = 0; i < 10; i++) {

            ArrayList<Quadrado> quadrados = new ArrayList<>();

            for(int j = 0; j < 10; j++) {

                Quadrado quadrado = new Quadrado();
                quadrados.add(quadrado);

            }

            Linha linha = new Linha(quadrados);
            linhas.put(Converter.intToString(i), linha);

        }

        gridAliada = new GridPlayer(linhas);

    }

    private void criarGridComputador() {

        Map<String, Linha> linhas = new HashMap<>();

        for(int i = 0; i < 10; i++) {

            ArrayList<Quadrado> quadrados = new ArrayList<>();

            for(int j = 0; j < 10; j++) {

                Quadrado quadrado = new Quadrado();
                quadrados.add(quadrado);

            }

            Linha linha = new Linha(quadrados);
            linhas.put(Converter.intToString(i), linha);

        }

        gridInimiga = new GridInimiga(linhas);

    }
    
}

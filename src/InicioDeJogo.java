import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InicioDeJogo extends Thread{

    Scanner scan = new Scanner(System.in);
    private Grid gridAliada;
    private Grid gridInimiga;
    IA ia;
    int turno;

    public InicioDeJogo() {

        System.out.println("(1) Novo Jogo");
        System.out.println("(2) Carregar Jogo");

        int opt = scan.nextInt();
        scan.nextLine();

        if (opt == 1) {

            criarGrids();
            posicionarNavios();
            ia = new IA(gridAliada);

        } else {

            Save.carregarJogo(this);
            ia = new IA(gridAliada);

        }
        
        
        

        turno = 0;
        combate();

    }

    public IA getIa() {
        return ia;
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
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println();
            System.out.println();

            System.out.println("Turno inimigo: ");
            ia.atirar();
            gridAliada.listarGrid();
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        System.out.println("Onde voc?? deseja atacar?");

        String linha;
        int coluna = 0;
        boolean valido;

        do {

            //if(turno != 0)
                //scan.nextLine();


            do {

                System.out.print("Linha (x para salvar): ");
                linha = scan.nextLine();
                if (linha.equals("x")) {
                    Save salvamento = new Save(this);
                }

            } while(!Converter.checarValidade(linha));
            
            boolean errado = true;

            do {

                try {

                    System.out.print("Coluna: ");
                    coluna = Integer.parseInt(scan.nextLine());
                    errado = false;

                } catch(Exception e) {

                }

            } while (errado && coluna < 1 && coluna > 10);

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

    public void setGrids(Grid gridAliada, Grid gridInimiga) {
        this.gridAliada = gridAliada;
        this.gridInimiga = gridInimiga;
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
            linhas.put(Converter.intToStringUp(i), linha);

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
            linhas.put(Converter.intToStringUp(i), linha);

        }

        gridInimiga = new GridInimiga(linhas);

    }
    
}

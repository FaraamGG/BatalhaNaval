import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Grid {


    protected Map<String, Linha> linhas;

    protected Navio[] frota = new Navio[6];

    public Grid(Map<String, Linha> linhas) {

        this.linhas = linhas;
        
        frota[0] = new Navio("Encouraçado", 4);
        frota[1] = new Navio("Cruzador de batalha", 4);
        frota[2] = new Navio("Cruzador", 3);
        frota[3] = new Navio("Destroyer", 2);
        frota[4] = new Navio("Fragata", 2);
        frota[5] = new Navio("submarino", 1);


    }

    public Navio[] getNavios() {
        return frota;
    }

    public boolean quadradoFoiAtingido(String linha, int coluna) {

        return getQuadrado(Converter.intToStringUp(Converter.stringToInt(linha)-1), coluna).getAtingido();

    }

    public Quadrado getQuadrado(String linha, int coluna) {
        return linhas.get(Converter.intToStringUp(Converter.stringToInt(linha)-1)).getQuadrados().get(coluna-1);
    }

    public void listarGrid() {

        System.out.println("Nada");

    }

    public void atirar(String linha, int coluna) {

        linhas.get(Converter.intToStringUp(Converter.stringToInt(linha)-1)).getQuadrados().get(coluna-1).atingir();

    }

    public void colocarNavios() {

        for (int i = 0; i < frota.length; i++) {
            escolherLocalizacao(frota[i]);
        }

    }

    public void escolherLocalizacao(Navio navio) {
    
        System.out.println("Tentando");

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

                    //System.out.printf("(%d) Esquerda%n", x);
                    possibilidades.put(x,disponiveis);
                    x++;

                }
                

            }

            if ((coluna + tamanho) < 10) {

                disponiveis = checarDisponibilidade(coluna, coluna+tamanho, linha, linha);
                if (disponiveis != null) {

                    //System.out.printf("(%d) Direita%n", x);
                    
                    possibilidades.put(x,disponiveis);
                    x++;

                }

            }

            if ((linha - tamanho) > 0) {

                disponiveis = checarDisponibilidade(coluna, coluna, linha-tamanho, linha);
                if (disponiveis != null) {

                    //System.out.printf("(%d) Cima%n", x);
                    possibilidades.put(x,disponiveis);
                    x++;

                }

            }

            if ((linha + tamanho) < 10) {

                disponiveis = checarDisponibilidade(coluna, coluna, linha, linha+tamanho);
                if (disponiveis != null) {

                    //System.out.printf("(%d) Baixo%n", x);
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
                //System.out.println("Não há posição disponível");
                return false;
            }

        }

        return false;

    }

    public void escolherPossibilidade(Navio navio, Map<Integer, ArrayList<Quadrado>> possibilidades) {

    }

    public ArrayList<Quadrado> checarDisponibilidade(int colunaInicial, int colunaFinal, int linhaInicial, int linhaFinal) {

        ArrayList<Quadrado> quadrados = new ArrayList<>();

        for(int i = linhaInicial; i <= linhaFinal; i++){
            for (int j = colunaInicial; j <= colunaFinal; j++) {

                if(!linhas.get(Converter.intToStringUp(i-1)).quadrados.get(j-1).getDisponivel()){

                    return null;

                } else {
  
                    quadrados.add(linhas.get(Converter.intToStringUp(i-1)).quadrados.get(j-1));

                }
            }
        }

        return quadrados;
        
    }

    public void posicionarNavio(Navio navio, ArrayList<Quadrado> quadrados) {

        for(Quadrado quadrado : quadrados) {
            quadrado.colocarNavio(navio);
            for(Entry<String, Quadrado> quadradoAdjacente : getAdjacentes(quadrado).entrySet()) {
                quadradoAdjacente.getValue().setIndisponivel();
            }
        }

    }

    public Map<String, Quadrado> getAdjacentes(Quadrado quadrado) {

        Map<String, Quadrado> adjacentesMap = new HashMap<>();

        for(int linha = 0; linha < 10; linha++) {
            for(int coluna = 0; coluna < 10; coluna++) {
                if(linhas.get(Converter.intToStringUp(linha)).getQuadrados().get(coluna).equals(quadrado)) {

                    if(coluna != 0) {
                        Quadrado esquerda = linhas.get(Converter.intToStringUp(linha)).getQuadrados().get(coluna-1);
                        adjacentesMap.put("left", esquerda);
                    }
                    if(coluna != 9) {
                        Quadrado direita = linhas.get(Converter.intToStringUp(linha)).getQuadrados().get(coluna+1);
                        adjacentesMap.put("right", direita);

                    }
                    if(linha != 0) {

                        Quadrado cima = linhas.get(Converter.intToStringUp(linha-1)).getQuadrados().get(coluna);
                        adjacentesMap.put("up", cima);

                    }
                    if(linha != 9) {

                        Quadrado baixo = linhas.get(Converter.intToStringUp(linha+1)).getQuadrados().get(coluna);
                        adjacentesMap.put("down", baixo);

                    }
                    return adjacentesMap;
                }

            }
        }

        return null;

    }

    
}

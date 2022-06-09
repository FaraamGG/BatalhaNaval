public class Quadrado {

    private boolean atingido;
    private boolean temNavio;
    private boolean disponivel;
    private Navio navio;

    public Quadrado() {

        atingido = false;
        temNavio = false;
        disponivel = true;

    }

    public Navio tipoNavio() {
        return navio;
    }

    public void setIndisponivel() {
        disponivel = false;
    }

    public boolean getAtingido() {
        return atingido;
    }

    public boolean getNavio() {
        return temNavio;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void atingir() {

        atingido = true;

        if(temNavio) {
            navio.atingir();
        }

    }

    public void colocarNavio(Navio navio) {

        this.navio = navio;
        this.temNavio = true;
        this.disponivel = false;

    }
    
}

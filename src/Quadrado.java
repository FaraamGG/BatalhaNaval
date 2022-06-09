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

    public Quadrado(String atingido, String temNavio, String disponivel, Navio navio) {

        if(atingido.equals("true")) {
            this.atingido = true;
        } else {
            this.atingido = false;
        }
        
        if(temNavio.equals("true")) {
            this.temNavio = true;
        } else {
            this.temNavio = false;
        }

        if(disponivel.equals("true")) {
            this.disponivel = true;
        } else {
            this.disponivel = false;
        }

        this.navio = navio;
        

    }

    public String getDados() {
        String dados = String.format("%s;%s;%s;%s;", 
        (atingido ? "true" : "false"),
        (temNavio ? "true" : "false"),
        (disponivel ? "true" : "false"),
        (navio == null ? "null" : navio.getNome())
        );
        return dados;
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

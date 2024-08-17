package classe ;
public class Etudiant{
    private String matricule;
    private String nom;
    private String prenom;
    private double moyenne;
    public Etudiant(){}
    public Etudiant(String matricule, String nom, String prenom, double moyenne) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.moyenne = moyenne;
    }
    public String getMatricule() {
        return matricule;
    }
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public double getMoyenne() {
        return moyenne;
    }
    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public String displayEtudiant(){
        return "{ Nom : " + this.nom+" ; Prenom : " + this.prenom+" ; Moyenne : " + this.moyenne +" }";
    }
}